package com.messon.faculty.app.presentation.mapper

import com.messon.faculty.app.data.remote.dto.ComplaintDto
import com.messon.faculty.app.data.remote.dto.ComplaintStatusDto
import com.messon.faculty.app.data.remote.dto.FetchComplaintRespDto
import com.messon.faculty.app.data.remote.dto.UpdateComplaintStatusDto
import com.messon.faculty.app.domain.dataModel.Complaint
import com.messon.faculty.app.domain.dataModel.ComplaintStatus
import com.messon.faculty.app.domain.dataModel.FetchComplaintsRes
import com.messon.faculty.app.domain.dataModel.UpdateComplaintStatus
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun FetchComplaintRespDto.toDomain(): FetchComplaintsRes {
    return FetchComplaintsRes(
        complaints = complaints.map {
            it.toDomain()
        },
        nextCursor = nextCursor
    )
}

fun ComplaintDto.toDomain(): Complaint {
    return Complaint(
        id = complaintId,
        studentName = studentName,
        studentId = studentRollNo,
        title = subject,
        description = description,
        status = complaintStatus.toDomain(),
        date = createdAt.formatDate(),
        likes = likesCount,
        dislikes = dislikesCount,
        userInteraction = userInteraction
    )
}

fun ComplaintStatusDto.toDomain(): ComplaintStatus {
    return when (this) {
        ComplaintStatusDto.PENDING -> ComplaintStatus.PENDING
        ComplaintStatusDto.PROGRESS -> ComplaintStatus.IN_PROGRESS
        ComplaintStatusDto.RESOLVED -> ComplaintStatus.RESOLVED
    }
}


fun UpdateComplaintStatus.toDto(): UpdateComplaintStatusDto{
    return UpdateComplaintStatusDto(
        complaintId = complaintId,
        complaintStatus = complaintStatus.toDto()
    )
}

fun UpdateComplaintStatusDto.toDomain(): UpdateComplaintStatus{
    return UpdateComplaintStatus(
        complaintId = complaintId,
        complaintStatus = complaintStatus.toDomain()
    )
}

fun ComplaintStatus.toDto(): ComplaintStatusDto {
    return when (this) {
        ComplaintStatus.PENDING -> ComplaintStatusDto.PENDING
        ComplaintStatus.IN_PROGRESS -> ComplaintStatusDto.PROGRESS
        ComplaintStatus.RESOLVED -> ComplaintStatusDto.RESOLVED
    }
}




fun String.formatDate(): String {
    return try {
        val instant = Instant.parse(this)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val day = dateTime.dayOfMonth.toString().padStart(2, '0')
        val month = when (dateTime.monthNumber) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> "Unknown"
        }
        val year = dateTime.year
        val hour = if (dateTime.hour % 12 == 0) 12 else dateTime.hour % 12
        val minute = dateTime.minute.toString().padStart(2, '0')
        val ampm = if (dateTime.hour < 12) "AM" else "PM"

        "$day $month $year, $hour:$minute $ampm"
    } catch (e: Exception) {
        try {
            val parts = this.split("T")
            if (parts.size == 2) {
                val datePart = parts[0]
                val timePart = parts[1].substring(0, 5)
                "$datePart $timePart"
            } else {
                this
            }
        } catch (e2: Exception) {
            this
        }
    }
}