package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.FilePicker
import com.messon.faculty.app.utils.FilePickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FilePickerViewModel : ViewModel() {

    private val filePicker = FilePicker()

    private val _state =
        MutableStateFlow<FilePickerState>(FilePickerState.Idle)

    val state: StateFlow<FilePickerState> = _state.asStateFlow()

    fun pickCsvFile() {
        viewModelScope.launch {

            _state.value = FilePickerState.Loading

            try {
                val file = filePicker.pickCsvFile()

                if (file != null) {

                    val content = withContext(Dispatchers.IO) {
                        file.bytes.decodeToString()
                    }

                    _state.value = FilePickerState.Success(
                        selectedFile = file,
                        fileContent = content
                    )

                } else {
                    _state.value = FilePickerState.Error("No file selected")
                }

            } catch (e: Exception) {
                _state.value =
                    FilePickerState.Error("Error: ${e.message}")
            }
        }
    }

    fun clear() {
        _state.value = FilePickerState.Idle
    }
}
