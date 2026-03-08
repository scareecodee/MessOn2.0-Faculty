package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.ManagerCreateReq
import com.messon.faculty.app.domain.repository.ManagerRepository
import com.messon.faculty.app.presentation.ui.uiState.AddManagerState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddManagerVM(
    private val managerRepository: ManagerRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _uiState = MutableStateFlow<AddManagerState>(AddManagerState.Idle)
    val uiState: StateFlow<AddManagerState> = _uiState.asStateFlow()

    fun addManager(manager: ManagerCreateReq,messId: String) {
        _uiState.value = AddManagerState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value = AddManagerState.Error("Session expired. Please login again.")
                return@launch
            }

            managerRepository.addManager(
                token = token,
                managerCreateReq = manager,
                messId = messId
            )
                .onSuccess {
                    _uiState.value = AddManagerState.Success(it)
                }
                .onError {
                    _uiState.value = AddManagerState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= AddManagerState.Idle
    }
}

