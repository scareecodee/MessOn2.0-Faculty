package com.messon.faculty.app.presentation.ui.uiState

import com.messon.faculty.app.domain.dataModel.HolidayRes
import com.messon.faculty.app.domain.dataModel.MessCreateRes

sealed class AddMessState {
    object Idle : AddMessState()
    object Loading : AddMessState()
    data class Success(val messCreateRes: MessCreateRes) : AddMessState()
    data class Error(val message: String) : AddMessState()
}

sealed class FetchMessState {
    object Idle :  FetchMessState()
    object Loading : FetchMessState()
    data class Success(val messes:List<MessCreateRes>) : FetchMessState()
    data class Error(val message: String) : FetchMessState()
}

sealed class SaveHolidayState{
    object Idle :  SaveHolidayState()
    object Loading : SaveHolidayState()
    data class Success(val holiday: HolidayRes) : SaveHolidayState()
    data class Error(val message: String) : SaveHolidayState()
}


sealed class FetchHolidaysState{
    object Idle :  FetchHolidaysState()
    object Loading : FetchHolidaysState()
    data class Success(val holidays:List<HolidayRes>) : FetchHolidaysState()
    data class Error(val message: String) : FetchHolidaysState()
}