package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchMessState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessListVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _selectedMess = MutableStateFlow<MessCreateRes?>(null)

    var selectedMess: StateFlow<MessCreateRes?> = _selectedMess.asStateFlow()


    fun selectMess(mess: MessCreateRes){
        _selectedMess.value=mess
    }

    fun clearSelectedMess(){
        _selectedMess.value=null
    }

    private val _uiState = MutableStateFlow<FetchMessState>(FetchMessState.Idle)
    val uiState: StateFlow<FetchMessState> = _uiState.asStateFlow()

    fun fetchAllMess() {
        _uiState.value = FetchMessState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    FetchMessState.Error("Session expired. Please login again")
                return@launch
            }

            messRepository.fetchAllMess(
                token = token,
            )
                .onSuccess {
                    _uiState.value = FetchMessState.Success(it)
                }
                .onError {
                    _uiState.value = FetchMessState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= FetchMessState.Idle
    }
}
