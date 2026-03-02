package com.messon.faculty.app.presentation.viewmodel



import androidx.lifecycle.ViewModel
import com.messon.faculty.app.domain.dataModel.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StudentDetailScreenVM : ViewModel() {

    private val _selectedStudent = MutableStateFlow<Student?>(null)

    var selectedStudent: StateFlow<Student?> = _selectedStudent.asStateFlow()

    fun selectStudent(student: Student){
        _selectedStudent.value=student
    }

    fun clearSelectedStudent(){
        _selectedStudent.value=null
    }


}