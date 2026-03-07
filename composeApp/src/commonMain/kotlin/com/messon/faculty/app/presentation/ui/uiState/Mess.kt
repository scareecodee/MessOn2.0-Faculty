package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.MessCreateRes

sealed class AddMessState {
    object Idle : AddMessState()
    object Loading : AddMessState()
    data class Success(val messCreateRes: MessCreateRes) : AddMessState()
    data class Error(val message: String) : AddMessState()
}

sealed class FetchMessState {
    object Idle :  FetchMessState()
    object Loading : FetchMessState()
    data class Success(val messes:List<MessCreateRes>) : FetchMessState()
    data class Error(val message: String) : FetchMessState()
}

