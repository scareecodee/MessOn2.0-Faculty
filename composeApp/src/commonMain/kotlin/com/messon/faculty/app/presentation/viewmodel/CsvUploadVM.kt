package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.domain.dataModel.CsvPreviewRes
import com.messon.faculty.app.domain.dataModel.PlatformFile
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.utils.CsvUploadState
import com.messon.faculty.app.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CsvUploadViewModel(
    private val repository: CsvRepository,
    ) : ViewModel() {

    private val _state =
        MutableStateFlow<CsvUploadState>(CsvUploadState.Idle)

    val state: StateFlow<CsvUploadState> = _state.asStateFlow()

    fun uploadCsv(
        file: PlatformFile
    ) {

        viewModelScope.launch {

            _state.value = CsvUploadState.Loading
            when (val result = repository.uploadCsv( file)) {

                is Result.Success -> {


                    _state.value = CsvUploadState.Success(
                        csvPreviewRes = result.data
                    )

                }

                is Result.Error -> {

                    _state.value = CsvUploadState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun resetState() {
        _state.value = CsvUploadState.Idle
    }
}