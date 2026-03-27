package com.messon.faculty.app.presentation.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.AddStudentsManuallyState
import com.messon.faculty.app.utils.Result
import com.messon.faculty.app.presentation.ui.uiState.ToggleStatusState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentDetailScreenVM(
   val studentRepository: StudentRepository,
    val secureStorage: AuthSecureStorage
)
    : ViewModel() {

    private val _selectedStudent = MutableStateFlow<Student?>(null)

    var selectedStudent: StateFlow<Student?> = _selectedStudent.asStateFlow()

    var _toggleStatusState= MutableStateFlow<ToggleStatusState>(ToggleStatusState.Idle)
    var toggleStatusState: StateFlow<ToggleStatusState> = _toggleStatusState.asStateFlow()



    fun selectStudent(student: Student){
        _selectedStudent.value=student
    }

    fun clearSelectedStudent(){
        _selectedStudent.value=null
    }

    fun toggleStatus(
        studentId: String
    ) {

        if (_toggleStatusState.value is ToggleStatusState.Loading) return

        viewModelScope.launch {
            _toggleStatusState.value = ToggleStatusState.Loading
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _toggleStatusState.value =
                    ToggleStatusState.Error("Session expired. Please login again")
                return@launch
            }
            when (val result = studentRepository.toggleStatus(
                token=token,
                uuid =studentId
            )) {

                is Result.Success -> {

                    _toggleStatusState.value = ToggleStatusState.Success(
                       toggleStatusRes = result.data
                    )

                    _selectedStudent.value= _selectedStudent.value?.copy(
                        isActive=result.data.isActive
                    )

                }

                is Result.Error -> {

                    _toggleStatusState.value = ToggleStatusState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }


    fun clearToggleStatusState(){
        _toggleStatusState.value= ToggleStatusState.Idle
    }


}