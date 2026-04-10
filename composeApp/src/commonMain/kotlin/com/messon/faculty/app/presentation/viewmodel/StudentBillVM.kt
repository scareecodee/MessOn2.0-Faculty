package com.messon.faculty.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.messon.faculty.app.core.auth.AuthSecureStorage
import com.messon.faculty.app.domain.dataModel.BillReq
import com.messon.faculty.app.domain.repository.StudentRepository
import com.messon.faculty.app.presentation.ui.uiState.CsvDownloadState
import com.messon.faculty.app.presentation.ui.uiState.StudentBillState
import com.messon.faculty.app.utils.BillingRangeType
import com.messon.faculty.app.utils.PredefinedOptions
import com.messon.faculty.app.utils.onError
import com.messon.faculty.app.utils.onSuccess
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.sink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.io.buffered
import kotlin.onFailure
import kotlin.time.Clock

class StudentBillVM(
    private val studentRepository: StudentRepository,
    private val secureStorage: AuthSecureStorage,
)
    : ViewModel() {

    private val _rangeType = MutableStateFlow<BillingRangeType?>(null)
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
    

    private val _billState = MutableStateFlow<StudentBillState>(StudentBillState.Idle)
    val consumptionState: StateFlow<StudentBillState> = _billState.asStateFlow()

    fun fetchMealConsumption(studentId: String) {
        if (_billState.value is StudentBillState.Loading) return
        _billState.value = StudentBillState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()
            if (studentId.isEmpty()) {
                _billState.value = StudentBillState.Error("Please select student")
                return@launch
            }
            if (token.isNullOrBlank()) {
                _billState.value =
                    StudentBillState.Error("Session expired. Please login again.")
                return@launch
            }
            val billReq = buildBillRequest() ?: run {
                _billState.value = StudentBillState.Error("Invalid input")
                return@launch
            }

            studentRepository.fetchMealConsumption(
                token = token,
                uuid = studentId,
                billReq =billReq,
            )
                .onSuccess {
                    val Log= Logger.withTag("view model")
                    Log.d(tag = "view model", message = {it.toString()})
                    _billState.value = StudentBillState.Success(it)
                }
                .onError {
                    _billState.value = StudentBillState.Error(it.toString())
                }
        }
    }

    fun selectPredefinedOption(option: PredefinedOptions) {
        _predefinedOption.value = option
        _rangeType.value = BillingRangeType.LAST_N_DAYS
        _fromDate.value = null
        _endDate.value = null
    }

    private val _downloadingState = MutableStateFlow<CsvDownloadState>(CsvDownloadState.Idle)
    val downloadingState = _downloadingState.asStateFlow()


//    fun downloadMessBillCsv() {
//        if (_downloadingState.value is CsvDownloadState.Loading) return
//        _downloadingState.value = CsvDownloadState.Loading
//
//        viewModelScope.launch(Dispatchers.IO) {
//
//            val token = secureStorage.getAccessToken()
//            if (messId == null) {
//                _downloadingState.value = CsvDownloadState.Error("Please select mess")
//                return@launch
//            }
//
//            if (token.isNullOrBlank()) {
//                _downloadingState.value =
//                    CsvDownloadState.Error("Session expired. Please login again.")
//                return@launch
//            }
//
//            val billReq = buildBillRequest() ?: run {
//                _downloadingState.value = CsvDownloadState.Error("Invalid input")
//                return@launch
//            }
//
//            val result= messRepository.downloadMessBillCsv(
//                token = token,
//                messId = messId,
//                billingReq = billReq,
//            )
//            result
//                .onSuccess { bytes ->
//                    val Log= Logger.withTag("cmp")
//                    Log.d(tag = "cmp_debug", message = {bytes.decodeToString()})
//                    val saveResult = saveCsvFile(bytes)
//
//                    saveResult
//                        .onSuccess { path ->
//                            _downloadingState.value = CsvDownloadState.Success(path)
//                        }
//                        .onFailure{
//                            _downloadingState.value =CsvDownloadState.Error(it.message ?: "File save failed")
//
//                        }
//                }
//                .onError {
//                    _downloadingState.value = CsvDownloadState.Error(it.toString())
//                }
//        }
//    }


//    suspend fun saveCsvFile(bytes: ByteArray): Result<String> {
//        return try {
//
//            val file = withContext(Dispatchers.Main) {
//                FileKit.openFileSaver(
//                    suggestedName = "${selected.value?.name?:"MessBill"}_${Clock.System.now().toEpochMilliseconds()}",
//                    extension = "csv"
//                )
//            } ?: return Result.failure(Exception("User cancelled"))
//
//            withContext(Dispatchers.IO) {
//                file.sink().buffered().use { sink ->
//                    sink.write(bytes)
//                }
//            }
//
//            Result.success(file.absolutePath())
//
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    private fun buildBillRequest(): BillReq? {
        val type = _rangeType.value

        return when (type) {
            BillingRangeType.LAST_N_DAYS -> {
                val option = _predefinedOption.value ?: return null
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
                if (from.isNullOrEmpty() || to.isNullOrEmpty()) return null

                BillReq(
                    rangeType = BillingRangeType.CUSTOM.value,
                    numOfDays = null,
                    startDate = from,
                    endDate = to
                )
            }

            else -> null
        }
    }

}

