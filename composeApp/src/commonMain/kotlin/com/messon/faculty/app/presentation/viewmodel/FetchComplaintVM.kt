package com.messon.faculty.app.presentation.viewmodel

import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.repository.MessRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.messon.faculty.app.core.Paginator
import com.messon.faculty.app.domain.dataModel.ComplaintStatus
import com.messon.faculty.app.domain.dataModel.FetchComplaintsRes
import com.messon.faculty.app.domain.dataModel.UpdateComplaintStatus
import com.messon.faculty.app.presentation.ui.uiState.FetchComplaintState
import com.messon.faculty.app.presentation.ui.uiState.UpdateComplaintStatusState
import com.messon.faculty.app.utils.NetworkError
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.messon.faculty.app.utils.Result
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@OptIn(FlowPreview::class)
class FetchComplaintsVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,
) : ViewModel() {

    private val _messId = MutableStateFlow<String>("")
    private val _search = MutableStateFlow<String?>(null)
    private val _batch = MutableStateFlow<Int?>(null)

    init {
        viewModelScope.launch {
            combine(_search, _batch) { search, batch ->
                search to batch
            }
                .drop(1)
                .debounce(500)
                .distinctUntilChanged()
                .collect {
                    refreshSilently()
                }
        }
    }

    fun setSearch(search: String?) {
        _search.value = search
    }

    fun setMessId(id: String) {
        _messId.value = id
    }

    private val _state = MutableStateFlow(FetchComplaintState())
    val state = _state.asStateFlow()

    private val paginator = Paginator<String?, FetchComplaintsRes, NetworkError>(
        initialKey = null,

        onLoadUpdated = { isLoading ->
            _state.update {
                it.copy(
                    isLoadingMore = if (!it.isRefreshing) isLoading else false
                )
            }
        },

        onRequest = { cursor ->
            val token = secureStorage.getAccessToken()
            if (token.isNullOrBlank()) {
                return@Paginator Result.Error(NetworkError.UNAUTHORIZED)
            }
            messRepository.fetchComplaints(
                token = token,
                cursor = cursor,
                search = _search.value,
                messId = _messId.value
            )
        },

        getNextKey = { _, response ->
            response.nextCursor
        },

        onError = { error ->
            _state.update {
                it.copy(
                    error = error.toString(),
                    isRefreshing = false,
                    endReached = true
                )
            }
        },

        onSuccess = { response, _ ->
            _state.update { currentState ->
                val existingComplaints = currentState.complaints
                val newComplaints = response.complaints

                if (existingComplaints.isEmpty()) {
                    currentState.copy(
                        complaints = newComplaints,
                        error = null,
                        endReached = response.nextCursor == null,
                        isRefreshing = false
                    )
                } else {
                    val existingIds = existingComplaints.map { it.id }.toSet()
                    val uniqueNewComplaints = newComplaints.filter { it.id !in existingIds }
                    currentState.copy(
                        complaints = existingComplaints + uniqueNewComplaints,
                        error = null,
                        endReached = response.nextCursor == null,
                        isRefreshing = false
                    )
                }
            }
        },

        endReached = { _, response ->
            response.nextCursor == null
        }
    )

    fun loadFirstPage() {
        _state.update {
            it.copy(
                error = null,
                endReached = false,
                complaints = emptyList()
            )
        }

        paginator.reset()

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun loadNextItems() {
        if (state.value.endReached) return
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun manualRefresh() {
        paginator.reset()
        _state.update {
            it.copy(
                error = null,
                endReached = false,
                isRefreshing = true,
                complaints = emptyList()
            )
        }
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refreshSilently() {
        paginator.reset()
        _state.update {
            it.copy(
                error = null,
                endReached = false,
                isRefreshing = false,
                complaints = emptyList()
            )
        }
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }


    fun clearError() {
        _state.update { it.copy(error = null) }
    }



    private val _updateComplaintStatusState=MutableStateFlow<UpdateComplaintStatusState>(
        UpdateComplaintStatusState.Idle)
    
    val updateComplaintStatusState=_updateComplaintStatusState.value
    private val _complaintId = MutableStateFlow<String>("")
    fun setComplaintId(id: String) {
        _complaintId.value= id
    }
    
    fun updateComplaintStatus(complaintId: String,status: ComplaintStatus){
        if (_updateComplaintStatusState.value is UpdateComplaintStatusState.Loading) return
        _updateComplaintStatusState.value = UpdateComplaintStatusState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()
            if (token.isNullOrBlank()) {
                _updateComplaintStatusState.value =
                    UpdateComplaintStatusState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.updateComplaintStatus(
                token = token,
                updateComplaintStatus = UpdateComplaintStatus(
                    complaintId = complaintId,
                    complaintStatus =status
                ),
            )
                .onSuccess {
                    val Log= Logger.withTag("view model")
                    Log.d(tag = "view model", message = {it.toString()})
                    _updateComplaintStatusState.value = UpdateComplaintStatusState.Success(it)
                }
                .onError {
                    _updateComplaintStatusState.value = UpdateComplaintStatusState.Error(it.toString())
                }
        }
    }

    fun clearUpdateStatusError() {
        _updateComplaintStatusState.value= UpdateComplaintStatusState.Idle
    }
    
    
}