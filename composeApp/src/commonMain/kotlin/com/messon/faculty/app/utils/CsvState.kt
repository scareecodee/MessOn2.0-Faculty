package com.messon.faculty.app.utils

import com.messon.faculty.app.domain.dataModel.CsvPreviewRes

sealed class CsvState {
    object Idle : CsvState()
    object Loading : CsvState()
    data class Success(val csvPreviewRes: CsvPreviewRes) : CsvState()
    data class Error(val message: String) :  CsvState()
}


sealed class CsvSaveChangesState {
    object Idle : CsvSaveChangesState()
    object Loading :CsvSaveChangesState()
    data class Success(val status: Boolean) : CsvSaveChangesState()
    data class Error(val message: String) : CsvSaveChangesState()
}
