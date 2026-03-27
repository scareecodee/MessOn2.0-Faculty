package com.messon.faculty.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.Holiday
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchHolidaysState
import com.messon.faculty.app.presentation.ui.uiState.SaveHolidayState
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessClosureVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,
    )
    : ViewModel() {

    private val _saveHolidayState = MutableStateFlow<SaveHolidayState>(SaveHolidayState.Idle)
    val saveHolidayState: StateFlow<SaveHolidayState> = _saveHolidayState.asStateFlow()


    private val _fetchHolidaysState = MutableStateFlow<FetchHolidaysState>(FetchHolidaysState.Idle)
    val fetchHolidayState: StateFlow<FetchHolidaysState> = _fetchHolidaysState.asStateFlow()

    fun addHoliday(holiday: Holiday,messId: String) {
        _saveHolidayState.value = SaveHolidayState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _saveHolidayState.value = SaveHolidayState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.createHoliday(
                token = token,
                messId = messId,
                holiday = holiday
            )
                .onSuccess {
                    _saveHolidayState.value = SaveHolidayState.Success(it)
                }
                .onError {
                    _saveHolidayState.value = SaveHolidayState.Error(it.toString())
                }
        }
    }

    fun clearSaveState(){
        _saveHolidayState.value= SaveHolidayState.Idle
    }

    fun fetchHoliday(messId: String) {
        if(_fetchHolidaysState.value is FetchHolidaysState.Loading) return
        _fetchHolidaysState.value = FetchHolidaysState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _fetchHolidaysState.value= FetchHolidaysState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchHolidays(
                token = token,
                messId = messId,
            )
                .onSuccess {
                    _fetchHolidaysState.value = FetchHolidaysState.Success(it)
                }
                .onError {
                    _fetchHolidaysState.value = FetchHolidaysState.Error(it.toString())
                }
        }
    }


}

