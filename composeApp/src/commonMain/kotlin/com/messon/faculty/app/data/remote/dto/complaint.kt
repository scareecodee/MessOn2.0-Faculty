package com.messon.faculty.app.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComplaintDto(
    @SerialName("complaint_id")
    val complaintId: String,

    @SerialName("student_name")
    val studentName: String,

    @SerialName("student_roll_number")
    val studentRollNo: String,

    @SerialName("subject")
    val subject: String,

    @SerialName("description")
    val description: String,

    @SerialName("likes_count")
    val likesCount: Int,

    @SerialName("dislikes_count")
    val dislikesCount: Int,

    @SerialName("complaint_status")
    val complaintStatus: ComplaintStatusDto,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("user_vote")
    val userInteraction: UserInteraction?
)


@Serializable
data class FetchComplaintRespDto(
    @SerialName("complaints")
    val complaints:List<ComplaintDto>,

    @SerialName("next_cursor")
    val nextCursor: String?

)

@Serializable
 enum class ComplaintStatusDto {

    @SerialName("pending")
    PENDING,

    @SerialName("progress")
    PROGRESS,

    @SerialName("resolved")
    RESOLVED
}

@Serializable
enum class UserInteraction {
    @SerialName("like")
    LIKE,

    @SerialName("dislike")
    DISLIKE
}

@Serializable
data class  UpdateComplaintStatusDto(
    @SerialName("complaint_id")
    val complaintId: String,

    @SerialName("complaint_status")
    val complaintStatus: ComplaintStatusDto
)