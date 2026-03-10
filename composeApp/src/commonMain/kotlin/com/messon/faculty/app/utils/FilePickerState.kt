package com.messon.faculty.app.utils
import io.github.vinceglb.filekit.PlatformFile

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