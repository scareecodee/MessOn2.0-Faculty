package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StudentBillDto(

    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("roll_no")
    val rollNo: String,

    @SerialName("branch")
    val branch: String,

    @SerialName("batch")
    val batch: Int,

    @SerialName("total_billing_periods")
    val billingPeriod: Int,

    @SerialName("billing_start_date")
    val startDate: String,

    @SerialName("billing_end_date")
    val endDate: String,

    @SerialName("total_bill")
    val billAmount: Double

)


@Serializable
data class BillReqDto(

    @SerialName("range_type")
    val rangeType: String,

    @SerialName("n_days")
    val numOfDays:Int?,

    @SerialName("start_date")
    val startDate: String?,

    @SerialName("end_date")
    val endDate: String?,
)
