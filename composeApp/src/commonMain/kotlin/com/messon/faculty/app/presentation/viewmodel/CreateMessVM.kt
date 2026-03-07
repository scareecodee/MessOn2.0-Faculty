package com.messon.faculty.app.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.MessCreateReq
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.presentation.ui.uiState.AddMessState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateMessVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,

    )
    : ViewModel() {

    private val _uiState = MutableStateFlow<AddMessState>(AddMessState.Idle)
    val uiState: StateFlow<AddMessState> = _uiState.asStateFlow()

    fun addMess(mess: MessCreateReq) {
        _uiState.value = AddMessState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value = AddMessState.Error("Session expired. Please login again.")
                return@launch
            }

           messRepository.addMess(
               token = token,
               messCreateReq = mess,
           )
                .onSuccess {
                    _uiState.value = AddMessState.Success(it)
                }
                .onError {
                    _uiState.value = AddMessState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= AddMessState.Idle
    }
}

