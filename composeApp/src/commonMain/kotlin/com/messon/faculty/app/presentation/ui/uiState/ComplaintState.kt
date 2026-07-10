package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.Complaint
import com.messon.faculty.app.domain.dataModel.UpdateComplaintStatus


data class FetchComplaintState(
    val complaints: List<Complaint> = emptyList(),
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false
)



sealed class UpdateComplaintStatusState{
    object Idle :  UpdateComplaintStatusState()
    object Loading : UpdateComplaintStatusState()
    data class Success(val resp: UpdateComplaintStatus) :UpdateComplaintStatusState()
    data class Error(val message: String) :UpdateComplaintStatusState()
}
