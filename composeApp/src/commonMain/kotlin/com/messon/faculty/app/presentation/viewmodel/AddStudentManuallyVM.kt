package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.domain.dataModel.StudentInput
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.mapper.toDto
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddStudentManuallyVM(
    private val repository: StudentRepository,

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
    ){
        viewModelScope.launch {

            _state.value = AddStudentsManuallyState.Loading
            val dtoList = students.value.map { it.toDto() }
            when (val result = repository.addStudentsManually(students =dtoList)) {

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