package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.app.presentation.ui.uiState.CsvState
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddStudentManuallyVM(
    private val repository: StudentRepository,
    private val secureStorage: AuthSecureStorage

    ) : ViewModel() {

    private val _students = MutableStateFlow<List<StudentInput>>(emptyList())

    val students: StateFlow<List<StudentInput>> = _students.asStateFlow()

    private val _state =
        MutableStateFlow<AddStudentsManuallyState>(AddStudentsManuallyState.Idle)

    val state: StateFlow<AddStudentsManuallyState> = _state.asStateFlow()

    fun addStudent(student: StudentInput) {
            _students.value += student
    }

    fun removeStudent(student: StudentInput) {
            _students.value -= student
    }

    fun getCurrentStudents(): List<StudentInput> {
        return _students.value
    }

    fun clearCurrentStudents(){
        _students.value=listOf<StudentInput>()
    }

    fun addStudentsToDb(
        messId: String
    ){
        viewModelScope.launch (Dispatchers.IO){
            _state.value = AddStudentsManuallyState.Loading
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _state.value =
                    AddStudentsManuallyState.Error("Session expired. Please login again")
                return@launch
            }

            when (val result = repository.addStudentsManually(
                students = students.value,
                token = token,
                messId =messId
            )) {

                is Result.Success -> {
                    _state.value = AddStudentsManuallyState.Success(
                        addStudentsManuallyRes = result.data
                    )

                }

                is Result.Error -> {

                    _state.value = AddStudentsManuallyState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun clearUiState(){
        _state.value= AddStudentsManuallyState.Idle
    }
}