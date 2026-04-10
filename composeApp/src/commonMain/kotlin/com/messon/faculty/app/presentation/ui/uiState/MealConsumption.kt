package com.messon.faculty.app.presentation.ui.uiState
import com.messon.faculty.app.domain.dataModel.StudentDetailedBillHistory

sealed class StudentBillState{
    object Idle :  StudentBillState()
    object Loading : StudentBillState()
    data class Success(val data: StudentDetailedBillHistory) : StudentBillState()
    data class Error(val message: String) : StudentBillState()
}