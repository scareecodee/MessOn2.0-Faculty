package com.messon.faculty.app.presentation.ui.uiState


import com.messon.faculty.app.domain.dataModel.ManagerCreateRes

sealed class AddManagerState{
    object Idle : AddManagerState()
    object Loading :AddManagerState()
    data class Success(val managerCreateRes: ManagerCreateRes) :AddManagerState()
    data class Error(val message: String) : AddManagerState()
}

sealed class FetchManagerState {
    object Idle :  FetchManagerState()
    object Loading : FetchManagerState()
    data class Success(val managers:List<ManagerCreateRes>) : FetchManagerState()
    data class Error(val message: String) :FetchManagerState()
}

