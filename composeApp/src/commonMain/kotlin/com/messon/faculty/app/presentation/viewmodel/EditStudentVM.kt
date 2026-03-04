package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.EditStudentState
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class EditStudentVM(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _updatedStudent=MutableStateFlow<Student?>(null)
    val updatedStudent=_updatedStudent.asStateFlow()

    private val _updateState= MutableStateFlow<EditStudentState>(EditStudentState.Idle)
    val updateState=_updateState.asStateFlow()

    fun setUpdateStudent(student: Student){
        _updatedStudent.value=student
    }

    fun editStudent(
        uuid: String
    ) {

        if(_updatedStudent.value==null) return
        viewModelScope.launch {


            _updateState.value = EditStudentState.Loading
            when (val result = studentRepository.editStudent(uuid=uuid,updatedStudent= _updatedStudent.value!!)) {

                is com.messon.faculty.app.utils.Result.Success -> {


                    _updateState.value = EditStudentState.Success(
                       editStudentRes = result.data
                    )

                }

                is Result.Error -> {

                    _updateState.value = EditStudentState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }
}