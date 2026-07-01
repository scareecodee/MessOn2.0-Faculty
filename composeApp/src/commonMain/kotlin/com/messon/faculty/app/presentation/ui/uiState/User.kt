package com.messon.faculty.app.presentation.ui.uiState


sealed class DeleteUserState{
    object Idle : DeleteUserState()
    object Loading : DeleteUserState()
    data class Success(val resp: String) : DeleteUserState()
    data class Error(val message: String) : DeleteUserState()
}