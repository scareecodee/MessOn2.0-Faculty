package com.messon.faculty.app.domain.dataModel

data class Manager(
    val id: Int,
    val name: String,
    val email: String="",
)

data class Mess(
    val id: Int,
    val name: String,
    val managers: List<Manager>,
    val status: Boolean,
    val location: String="D Block G Floor"
)

data class MessCreateReq(
    val name: String,
    val isActive: Boolean,
    val location: String
)


data class MessCreateRes(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val location: String
)


data class Holiday(
    val startDate: String,
    val endDate: String,
    val reason: String

)

data class HolidayRes(
    val holidayId: String,
    val startDate: String,
    val endDate: String,
    val reason: String
)