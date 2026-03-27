package com.messon.faculty.app.data.remote.dto

import com.messon.faculty.app.domain.dataModel.Conflict
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StudentDto(
    @SerialName("student_uuid")
    val uuid: String,

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

    @SerialName("is_active")
    val isActive: Boolean,

    @SerialName("picture")
    val profileImg: String?
)

@Serializable
data class StudentInputDto(

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

    @SerialName("is_active")
    val isActive: Boolean,

)

@Serializable
data class FetchStudentsResDto(
    @SerialName("students_list")
    val students:List<StudentDto>,

    @SerialName("next_cursor")
    val nextCursor: String?
)




@Serializable
data class AddStudentsManuallyResDto(
    @SerialName("status")
    val status: Boolean,

    @SerialName("conflicts")
    val conflicts:List<ConflictDto>
)

@Serializable
data class ConflictDto(
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


@Serializable
data class EditStudentResDto(
    @SerialName("status")
    val status: Boolean,

    @SerialName("student_details")
    val updatedStudent: StudentDto?,

    @SerialName("conflicts")
    val conflicts: ConflictDto?

)

