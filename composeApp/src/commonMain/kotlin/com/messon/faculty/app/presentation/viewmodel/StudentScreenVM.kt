package com.messon.faculty.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.Paginator
import com.messon.faculty.app.domain.dataModel.FetchStudentsRes
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchStudentsState
import com.messon.faculty.app.utils.NetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentScreenVM(
    private val studentRepository: StudentRepository
) : ViewModel() {

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
            studentRepository.fetchStudents(cursor)
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

    fun loadIfEmpty() {
        if (_state.value.students.isNotEmpty()) return
        refresh()
    }

    fun loadNextItems() {
        if (state.value.endReached) return

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refresh() {
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
}