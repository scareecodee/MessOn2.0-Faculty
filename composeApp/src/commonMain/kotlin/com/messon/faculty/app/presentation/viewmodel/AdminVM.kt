package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.AdminCreateReq
import com.messon.faculty.app.domain.dataModel.DeleteUsers
import com.messon.faculty.app.domain.repository.AdminRepository
import com.messon.faculty.app.presentation.ui.uiState.AddAdminState
import com.messon.faculty.app.presentation.ui.uiState.DeleteUserState
import com.messon.faculty.app.presentation.ui.uiState.FetchAdminState
import com.messon.faculty.app.utils.UserRole
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminVM(
    private val adminRepository: AdminRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _uiState = MutableStateFlow<AddAdminState>(AddAdminState.Idle)
    val uiState: StateFlow<AddAdminState> = _uiState.asStateFlow()

    fun addAdmin(admin: AdminCreateReq) {
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

    private val _fetchState = MutableStateFlow<FetchAdminState>(FetchAdminState.Idle)
    val fetchState: StateFlow<FetchAdminState> = _fetchState.asStateFlow()

    fun fetchAdmin() {
        if( _fetchState.value is FetchAdminState.Loading) return
        _fetchState.value = FetchAdminState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchState.value = FetchAdminState.Error("Session expired. Please login again.")
                return@launch
            }

            adminRepository.fetchAllAdmins(
                token = token,
            )
                .onSuccess {
                    _fetchState.value = FetchAdminState.Success(it)
                }
                .onError {
                    _fetchState.value = FetchAdminState.Error(it.toString())
                }
        }
    }

    fun clearFetchError(){
        _fetchState.value= FetchAdminState.Idle
    }

    private val _selectedAdmins =
        MutableStateFlow<Set<String>>(emptySet())

    val selectedAdmins =
        _selectedAdmins.asStateFlow()

    private val _deleteUserState = MutableStateFlow<DeleteUserState>(DeleteUserState.Idle)
    val deleteUserState: StateFlow<DeleteUserState> = _deleteUserState.asStateFlow()

    fun toggleSelection(id: String) {

        _selectedAdmins.value =
            _selectedAdmins.value.toMutableSet().apply {

                if (contains(id))
                    remove(id)
                else
                    add(id)
            }
    }

    fun clearSelection() {
        _selectedAdmins.value = emptySet()
    }


    fun deleteSelectedAdmins(){

        if(_deleteUserState.value is DeleteUserState.Loading) return
        _deleteUserState.value = DeleteUserState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _deleteUserState.value = DeleteUserState.Error("Session expired. Please login again.")
                return@launch
            }

            adminRepository.deleteUsers(
                token = token,
                deleteUsers = DeleteUsers(
                    role = UserRole.COLLEGE_ADMIN.value,
                    userIds = _selectedAdmins.value.toList()
                ),
            )
                .onSuccess {
                    _deleteUserState.value = DeleteUserState.Success(it)
                }
                .onError {
                    _deleteUserState.value = DeleteUserState.Error(it.toString())
                }
        }
    }

    fun clearDeleteAdminsError(){
        _deleteUserState.value= DeleteUserState.Idle
    }
}

