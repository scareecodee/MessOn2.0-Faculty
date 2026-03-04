package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
import com.messon.faculty.app.domain.dataModel.EditStudentRes
import com.messon.faculty.app.domain.dataModel.Student
import com.messon.faculty.app.domain.dataModel.ToggleStatusRes

sealed class AddStudentsManuallyState {
    object Idle :AddStudentsManuallyState()
    object Loading : AddStudentsManuallyState()
    data class Success(val addStudentsManuallyRes: AddStudentsManuallyRes) : AddStudentsManuallyState()
    data class Error(val message: String) :  AddStudentsManuallyState()
}


sealed class ToggleStatusState{
    object Idle : ToggleStatusState()
    object Loading : ToggleStatusState()
    data class Success(val toggleStatusRes: ToggleStatusRes) : ToggleStatusState()
    data class Error(val message: String) :  ToggleStatusState()
}



data class FetchStudentsState(
    val students: List<Student> = emptyList(),
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean=false,
    val error: String? = null,
    val endReached: Boolean = false
)


sealed class EditStudentState{
    object Idle : EditStudentState()
    object Loading :  EditStudentState()
    data class Success(val editStudentRes: EditStudentRes ) :  EditStudentState()
    data class Error(val message: String) :   EditStudentState()
}

