package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.AdminCreateReq
import com.messon.faculty.app.domain.repository.AdminRepository
import com.messon.faculty.app.presentation.ui.uiState.AddAdminState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddAdminVM(
    private val adminRepository: AdminRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _uiState = MutableStateFlow<AddAdminState>(AddAdminState.Idle)
    val uiState: StateFlow<AddAdminState> = _uiState.asStateFlow()

    fun addMess(admin: AdminCreateReq) {
        _uiState.value = AddAdminState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value = AddAdminState.Error("Session expired. Please login again.")
                return@launch
            }

                adminRepository.addAdmin(
                token = token,
                adminCreateReq = admin
            )
                .onSuccess {
                    _uiState.value = AddAdminState.Success(it)
                }
                .onError {
                    _uiState.value = AddAdminState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= AddAdminState.Idle
    }
}

