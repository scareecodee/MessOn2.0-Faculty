package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.repository.CsvRepository
import com.messon.faculty.app.presentation.ui.uiState.CsvSaveChangesState
import com.messon.faculty.app.presentation.ui.uiState.CsvState
import com.messon.faculty.app.utils.Result
import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CsvUploadViewModel(
    private val repository: CsvRepository,
    private val secureStorage: AuthSecureStorage,

    ) : ViewModel() {

    private val _state =
        MutableStateFlow<CsvState>(CsvState.Idle)

    val state: StateFlow<CsvState> = _state.asStateFlow()

    private val _saveChangesState =
        MutableStateFlow<CsvSaveChangesState>(CsvSaveChangesState.Idle)

    val saveChangesState: StateFlow<CsvSaveChangesState> = _saveChangesState.asStateFlow()


    fun uploadCsv(
        messId: String,
        mode: String,
        file: PlatformFile
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _state.value =
                    CsvState.Error("Session expired. Please login again")
                return@launch
            }

            _state.value = CsvState.Loading
            when (val result = repository.uploadCsv(
                file = file, mode = mode, messId = messId,
                token = token
            )) {

                is Result.Success -> {


                    _state.value = CsvState.Success(
                        csvPreviewRes = result.data
                    )

                }

                is Result.Error -> {

                    _state.value = CsvState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun resetState() {
        _state.value = CsvState.Idle
    }

    fun  saveChanges(
        messId: String,
        importToken: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _state.value =
                    CsvState.Error("Session expired. Please login again")
                return@launch
            }
            _saveChangesState.value = CsvSaveChangesState.Loading
            when (val result = repository.saveChanges(
                importToken = importToken,
                token = token,
                messId = messId
            )) {

                is Result.Success -> {


                    _saveChangesState.value = CsvSaveChangesState.Success(
                        isActive= result.data.status
                    )

                }

                is Result.Error -> {

                    _saveChangesState.value = CsvSaveChangesState.Error(
                        message = result.error.name
                    )
                }
            }
        }
    }

    fun resetSaveChangesState() {
        _saveChangesState.value = CsvSaveChangesState.Idle
    }
}