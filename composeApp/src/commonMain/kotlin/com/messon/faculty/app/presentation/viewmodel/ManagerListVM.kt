package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.ManagerCreateRes
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.domain.repository.ManagerRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchManagerState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManagerListVM(
    private val managerRepository: ManagerRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _selectedManager = MutableStateFlow<ManagerCreateRes?>(null)

    var selectedManager: StateFlow<ManagerCreateRes?> = _selectedManager.asStateFlow()


    fun selectManager(manager: ManagerCreateRes){
        _selectedManager.value=manager
    }

    fun clearSelectedManager(){
        _selectedManager.value=null
    }

    private val _uiState = MutableStateFlow<FetchManagerState>(FetchManagerState.Idle)
    val uiState: StateFlow<FetchManagerState> = _uiState.asStateFlow()

    fun fetchAllMess() {
        _uiState.value = FetchManagerState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    FetchManagerState.Error("Session expired. Please login again")
                return@launch
            }

            managerRepository.fetchAllManagers(
                token = token,
            )
                .onSuccess {
                    _uiState.value = FetchManagerState.Success(it)
                }
                .onError {
                    _uiState.value = FetchManagerState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= FetchManagerState.Idle
    }
}
