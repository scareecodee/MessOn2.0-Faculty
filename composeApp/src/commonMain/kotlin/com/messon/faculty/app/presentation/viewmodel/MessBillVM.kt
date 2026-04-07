package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.BillReq
import com.messon.faculty.app.domain.dataModel.MessCreateRes
import com.messon.faculty.app.domain.repository.MessRepository
import com.messon.faculty.app.presentation.ui.uiState.FetchMessBillState
import com.messon.faculty.app.utils.BillingRangeType
import com.messon.faculty.app.utils.PredefinedOptions
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessBillVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,
)
    : ViewModel() {

    private val _rangeType = MutableStateFlow<BillingRangeType?>(null)
    private val _selectedMess = MutableStateFlow<MessCreateRes?>(null)

    var selectedMess: StateFlow<MessCreateRes?> = _selectedMess.asStateFlow()

    private val _fromDate = MutableStateFlow<String?>(null)

    var fromDate: StateFlow<String?> = _fromDate.asStateFlow()

    private val _endDate= MutableStateFlow<String?>(null)

    var endDate: StateFlow<String?> = _endDate.asStateFlow()

    private val _predefinedOption= MutableStateFlow<PredefinedOptions?>(null)

    var predefinedOption: StateFlow<PredefinedOptions?> = _predefinedOption.asStateFlow()

    fun setFromDate(fromDate: String?) {
        _fromDate.value = fromDate
        _rangeType.value = BillingRangeType.CUSTOM

        _predefinedOption.value = null
    }

    fun setEndDate(endDate: String?) {
        _endDate.value = endDate
        _rangeType.value = BillingRangeType.CUSTOM

        _predefinedOption.value = null
    }


    fun selectMess(mess: MessCreateRes){
        _selectedMess.value=mess
    }

    fun clearSelectedMess(){
        _selectedMess.value=null
    }

    private val _fetchMessBillState = MutableStateFlow<FetchMessBillState>(FetchMessBillState.Idle)
    val fetchMessBillState: StateFlow<FetchMessBillState> = _fetchMessBillState.asStateFlow()

    fun fetchMessBill() {
        if (_fetchMessBillState.value is FetchMessBillState.Loading) return
        _fetchMessBillState.value = FetchMessBillState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()
            val messId = _selectedMess.value?.id ?: return@launch

            if (token.isNullOrBlank()) {
                _fetchMessBillState.value =
                    FetchMessBillState.Error("Session expired. Please login again.")
                return@launch
            }

            val type = _rangeType.value

            val billReq = when (type) {

                BillingRangeType.LAST_N_DAYS -> {
                    val option = _predefinedOption.value ?: run {
                        _fetchMessBillState.value =
                            FetchMessBillState.Error("Please select predefined option")
                        return@launch
                    }

                    BillReq(
                        rangeType = BillingRangeType.LAST_N_DAYS.value,
                        numOfDays = option.numDays,
                        startDate = null,
                        endDate = null
                    )
                }

                BillingRangeType.CUSTOM -> {
                    val from = _fromDate.value
                    val to = _endDate.value

                    if (from.isNullOrEmpty() || to.isNullOrEmpty()) {
                        _fetchMessBillState.value =
                            FetchMessBillState.Error("Please select both dates")
                        return@launch
                    }

                    BillReq(
                        rangeType = BillingRangeType.CUSTOM.value,
                        numOfDays = null,
                        startDate = from,
                        endDate = to
                    )
                }

                else -> {
                    _fetchMessBillState.value =
                        FetchMessBillState.Error("Please select billing range")
                    return@launch
                }
            }

            messRepository.fetchMessBill(
                token = token,
                messId = messId,
                billingReq = billReq,
            )
                .onSuccess {
                    _fetchMessBillState.value = FetchMessBillState.Success(it)
                }
                .onError {
                    _fetchMessBillState.value = FetchMessBillState.Error(it.toString())
                }
        }
    }

    fun selectPredefinedOption(option: PredefinedOptions) {
        _predefinedOption.value = option
        _rangeType.value = BillingRangeType.LAST_N_DAYS
        _fromDate.value = null
        _endDate.value = null
    }

}

