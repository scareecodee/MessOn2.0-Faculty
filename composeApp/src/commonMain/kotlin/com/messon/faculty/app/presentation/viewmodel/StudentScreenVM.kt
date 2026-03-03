package com.messon.faculty.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.Paginator
import com.messon.faculty.app.domain.dataModel.FetchStudentsRes
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchStudentsState
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

@OptIn(FlowPreview::class)
class StudentScreenVM(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _search=MutableStateFlow<String?>(null)
    private val _batch=MutableStateFlow<Int?>(null)

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

    fun setBatch(batch:Int?){
        _batch.value=batch
    }

    private val _state = MutableStateFlow(FetchStudentsState())
    val state = _state.asStateFlow()

    private val paginator = Paginator<String?, FetchStudentsRes, NetworkError>(
        initialKey = null,

        onLoadUpdated = { isLoading ->
            _state.update {
                it.copy(
                    isLoadingMore = if (!it.isRefreshing) isLoading else false
                )
            }
        },

        onRequest = { cursor ->
            studentRepository.fetchStudents(cursor=cursor, batch = _batch.value, search = _search.value)
        },

        getNextKey = { _, response ->
            response.cursor
        },

        onError = { error ->
            _state.update {
                it.copy(
                    error = error.toString(),
                    isRefreshing = false
                )
            }
        },

        onSuccess = { response, _ ->
            _state.update {
                it.copy(
                    students = it.students + response.students,
                    error = null,
                    endReached = response.cursor == null,
                    isRefreshing = false
                )
            }
        },

        endReached = { _, response ->
            response.cursor == null
        }
    )


    fun loadFirstPage() {
        _state.update {
            it.copy(
                students = emptyList(),
                error = null,
                endReached = false
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
                students = emptyList(),
                error = null,
                endReached = false,
                isRefreshing = true
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
                students = emptyList(),
                error = null,
                endReached = false,
                isRefreshing = false
            )
        }

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}