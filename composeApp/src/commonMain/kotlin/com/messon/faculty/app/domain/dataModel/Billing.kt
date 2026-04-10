package com.messon.faculty.app.domain.dataModel


data class StudentBillingData(
    val name: String,
    val rollNo: String,
    val branch: String,
    val batch: Int,
    val totalBillingPeriods: Int,
    val startDate: String,
    val endDate: String,
    val totalBill: Double
)

data class BillReq(
    val rangeType: String,
    val numOfDays:Int?,
    val startDate: String?,
    val endDate: String?,
)

data class StudentDetailedBillHistory(
    val history:List<StudentMealDay>,
    val bill:List<StudentBillingData>
)