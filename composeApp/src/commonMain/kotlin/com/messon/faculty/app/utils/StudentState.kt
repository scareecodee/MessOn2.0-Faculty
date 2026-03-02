package com.messon.faculty.app.utils

import com.messon.faculty.app.domain.dataModel.AddStudentsManuallyRes

sealed class AddStudentsManuallyState {
    object Idle :AddStudentsManuallyState()
    object Loading : AddStudentsManuallyState()
    data class Success(val addStudentsManuallyRes: AddStudentsManuallyRes) : AddStudentsManuallyState()
    data class Error(val message: String) :  AddStudentsManuallyState()
}
