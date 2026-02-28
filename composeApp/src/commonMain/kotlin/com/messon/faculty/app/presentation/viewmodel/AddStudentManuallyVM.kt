package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.domain.dataModel.StudentInput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddStudentManuallyVM : ViewModel() {

    private val _students = MutableStateFlow<List<StudentInput>>(emptyList())

    val students: StateFlow<List<StudentInput>> = _students.asStateFlow()

    fun addStudent(student: StudentInput) {
        viewModelScope.launch {
            _students.value += student
        }
    }

    fun removeStudent(student: StudentInput) {
        viewModelScope.launch {
            _students.value -= student
        }
    }

    fun getCurrentStudents(): List<StudentInput> {
        return _students.value
    }

    fun clearCurrentStudents(){
        _students.value=listOf<StudentInput>()
    }
}