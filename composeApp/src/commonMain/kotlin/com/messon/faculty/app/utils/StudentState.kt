package com.messon.faculty.app.utils

import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes
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