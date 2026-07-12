package com.messon.faculty.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.Paginator
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.DeleteUsers
import com.messon.faculty.app.domain.dataModel.FetchStudentsRes
import com.messon.faculty.app.domain.repository.AdminRepository
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.AddAdminState
import com.messon.faculty.app.presentation.ui.uiState.CsvState
import com.messon.faculty.app.presentation.ui.uiState.DeleteUserState
import com.messon.faculty.app.presentation.ui.uiState.FetchStudentsState
import com.messon.faculty.app.utils.NetworkError
import com.messon.faculty.app.utils.Result
import com.messon.faculty.app.utils.UserRole
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class StudentScreenVM(
    private val studentRepository: StudentRepository,
    private val adminRepository: AdminRepository,
    private val secureStorage: AuthSecureStorage,

    ) : ViewModel() {

    private val _messId = MutableStateFlow<String?>(null)
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

    fun setMessId(id: String) {
        _messId.value = id
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

            val token = secureStorage.getAccessToken()
            val messId = _messId.value

            if (token.isNullOrBlank() || messId.isNullOrBlank()) {
                return@Paginator Result.Error(NetworkError.UNAUTHORIZED)
            }

            studentRepository.fetchStudents(
                cursor = cursor,
                search = _search.value,
                token = token,
                messId = messId
            )
        },

        getNextKey = { _, response ->
            response.cursor
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

    private val _selectedStudents =
        MutableStateFlow<Set<String>>(emptySet())

    val selectedStudents =
        _selectedStudents.asStateFlow()


    private val _deleteUserState = MutableStateFlow<DeleteUserState>(DeleteUserState.Idle)
    val deleteUserState: StateFlow<DeleteUserState> = _deleteUserState.asStateFlow()


    fun toggleSelection(id: String) {

        _selectedStudents.value =
            _selectedStudents.value.toMutableSet().apply {

                if (contains(id))
                    remove(id)
                else
                    add(id)
            }
    }

    fun clearSelection() {
        _selectedStudents.value = emptySet()
    }


    fun deleteSelectedStudents(){

        if(_deleteUserState.value is DeleteUserState.Loading) return
        _deleteUserState.value = DeleteUserState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _deleteUserState.value = DeleteUserState.Error("Session expired. Please login again.")
                return@launch
            }

            adminRepository.deleteUsers(
                token = token,
                deleteUsers = DeleteUsers(
                    role = UserRole.STUDENT.value,
                    userIds = _selectedStudents.value.toList()
                ),
            )
                .onSuccess {
                    _deleteUserState.value = DeleteUserState.Success(it)
                }
                .onError {
                    _deleteUserState.value = DeleteUserState.Error(it.toString())
                }
        }
    }

    fun clearDeleteStudentsError(){
        _deleteUserState.value= DeleteUserState.Idle
    }
}