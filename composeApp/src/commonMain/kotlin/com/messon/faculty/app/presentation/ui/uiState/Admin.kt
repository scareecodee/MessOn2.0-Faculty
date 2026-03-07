package com.messon.faculty.app.presentation.ui.uiState
import com.messon.faculty.app.domain.dataModel.AdminCreateRes

sealed class AddAdminState{
    object Idle : AddAdminState()
    object Loading :AddAdminState()
    data class Success(val adminCreateRes: AdminCreateRes) :AddAdminState()
    data class Error(val message: String) : AddAdminState()
}

sealed class FetchAdminState {
    object Idle :  FetchAdminState()
    object Loading : FetchAdminState()
    data class Success(val admins:List<AdminCreateRes>) : FetchAdminState()
    data class Error(val message: String) :FetchAdminState()
}

