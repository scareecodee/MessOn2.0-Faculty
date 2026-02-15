package com.messon.faculty.app.utils

import com.messon.faculty.app.domain.dataModel.PlatformFile

sealed class FilePickerState {

    object Idle : FilePickerState()

    object Loading : FilePickerState()

    data class Success(
        val selectedFile: PlatformFile,
        val fileContent: String
    ) : FilePickerState()

    data class Error(
        val message: String
    ) : FilePickerState()
}