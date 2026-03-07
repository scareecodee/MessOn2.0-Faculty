package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.AdminCreateRes
import com.messon.faculty.app.domain.repository.AdminRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchAdminState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminListVM(
    private val adminRepository: AdminRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _selectedAdmin = MutableStateFlow<AdminCreateRes?>(null)

    var selectedAdmin: StateFlow<AdminCreateRes?> = _selectedAdmin.asStateFlow()


    fun selectMess(admin: AdminCreateRes){
        _selectedAdmin.value=admin
    }

    fun clearSelectedMess(){
        _selectedAdmin.value=null
    }

    private val _uiState = MutableStateFlow<FetchAdminState>(FetchAdminState.Idle)
    val uiState: StateFlow<FetchAdminState> = _uiState.asStateFlow()

    fun fetchAllMess() {
        _uiState.value = FetchAdminState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    FetchAdminState.Error("Session expired. Please login again")
                return@launch
            }

            adminRepository.fetchAllAdmins(
                token = token,
            )
                .onSuccess {
                    _uiState.value = FetchAdminState.Success(it)
                }
                .onError {
                    _uiState.value = FetchAdminState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= FetchAdminState.Idle
    }
}
