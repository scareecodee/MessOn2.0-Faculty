package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessCreateReqDto(

    @SerialName("name")
    val name: String,

    @SerialName("location")
    val location: String,

    @SerialName("is_active")
    val isActive: Boolean

)


@Serializable
data class MessCreateResDto(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("location")
    val location: String,

    @SerialName("is_active")
    val isActive: Boolean

)


@Serializable
data class HolidayDto(
    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,

    @SerialName("reason")
    val reason: String

)

@Serializable
data class HolidayResDto(
    @SerialName("closure_id")
    val holidayId: String,

    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,

    @SerialName("reason")
    val reason: String
)