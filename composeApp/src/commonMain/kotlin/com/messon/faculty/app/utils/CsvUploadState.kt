package com.messon.faculty.app.utils

import com.messon.faculty.app.domain.dataModel.CsvPreviewRes

sealed class CsvUploadState {
    object Idle : CsvUploadState()
    object Loading : CsvUploadState()
    data class Success(val csvPreviewRes: CsvPreviewRes) : CsvUploadState()
    data class Error(val message: String) :  CsvUploadState()
}
