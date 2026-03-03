package com.messon.faculty.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StudentDto(

    @SerialName("name")
    val name: String,

    @SerialName("roll_number")
    val studentId: String,

    @SerialName("email")
    val email: String,

    @SerialName("branch")
    val branch: String,

    @SerialName("batch")
    val batch: Int,

    @SerialName("isActive")
    val isActive: Boolean,

    @SerialName("picture")
    val profileImg: String?

)



@Serializable
data class AddStudentsManuallyResDto(
    @SerialName("status")
    val status: Boolean,

    @SerialName("conflicts")
    val conflicts:List<Conflict>
)

@Serializable
data class Conflict(
    @SerialName("email")
    val email: String,

    @SerialName("roll_number")
    val studentId: String,

    @SerialName("message")
    val message: String
)

@Serializable
data class ToggleStatusResDto(
    @SerialName("is_active")
    val isActive: Boolean
)