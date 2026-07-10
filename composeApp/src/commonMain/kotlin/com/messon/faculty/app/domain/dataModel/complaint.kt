package com.messon.faculty.app.domain.dataModel
import com.messon.faculty.app.data.remote.dto.ComplaintStatusDto
import com.messon.faculty.app.data.remote.dto.UserInteraction



data class Complaint(
    val id: String,
    val studentName: String,
    val studentId: String,
    val title: String,
    val description: String,
    val status: ComplaintStatus,
    val date: String,
    val likes: Int,
    val dislikes: Int,
    val userInteraction: UserInteraction? = null
)

data class FetchComplaintsRes(
    val complaints: List<Complaint>,
    val nextCursor: String?
)

enum class ComplaintStatus {
    PENDING,
    IN_PROGRESS,
    RESOLVED
}

data class  UpdateComplaintStatus(
    val complaintId: String,
    val complaintStatus: ComplaintStatus
)