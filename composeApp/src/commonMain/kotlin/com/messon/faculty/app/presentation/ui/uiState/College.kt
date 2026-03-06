package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.College

sealed class FetchCollegesState {
    object Idle : FetchCollegesState()
    object Loading : FetchCollegesState()
    data class Success(val colleges: List<College>) : FetchCollegesState()
    data class Error(val message: String) : FetchCollegesState()
}


