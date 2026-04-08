package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.StudentBillingData

sealed class FetchMessBillState{
    object Idle :  FetchMessBillState()
    object Loading : FetchMessBillState()
    data class Success(val bill:List<StudentBillingData>) :FetchMessBillState()
    data class Error(val message: String) :FetchMessBillState()
}


sealed class CsvDownloadState {
    object Idle : CsvDownloadState()
    object Loading : CsvDownloadState()
    data class Success(val path: String) : CsvDownloadState()
    data class Error(val message: String) : CsvDownloadState()
}