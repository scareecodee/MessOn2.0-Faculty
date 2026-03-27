package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.EditStudentState
import com.messon.faculty.app.presentation.ui.uiState.FetchManagerState
import com.messon.faculty.app.utils.Result
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class EditStudentVM(
    private val studentRepository: StudentRepository,
    private val secureStorage: AuthSecureStorage
) : ViewModel() {

    private val _updatedStudent=MutableStateFlow<Student?>(null)

    private val _updateState= MutableStateFlow<EditStudentState>(EditStudentState.Idle)
    val updateState=_updateState.asStateFlow()

    fun setUpdateStudent(student: Student){
        _updatedStudent.value=student
    }


    fun editStudent(
        uuid: String
    ) {
        if(_updatedStudent.value==null || _updateState.value is EditStudentState.Loading) return
        _updateState.value = EditStudentState.Loading

        viewModelScope.launch {
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _updateState.value =
                    EditStudentState.Error("Session expired. Please login again")
                return@launch
            }


            studentRepository.editStudent(
                token = token,
                uuid = uuid,
                updatedStudent = _updatedStudent.value!!
            )
                .onSuccess {
                    if(it.status){
                        _updatedStudent.value=it.updatedStudent
                    }
                    _updateState.value = EditStudentState.Success(it)
                }
                .onError {
                    _updateState.value = EditStudentState.Error(it.toString())
                }
        }
    }

    fun clearUpdateState(){
        _updateState.value= EditStudentState.Idle
    }
}