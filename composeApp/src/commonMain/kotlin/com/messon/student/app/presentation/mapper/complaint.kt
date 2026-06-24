package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.ComplaintDto
import com.messon.student.app.data.remote.dto.ComplaintStatusDto
import com.messon.student.app.data.remote.dto.ComplaintVoteReqDto
import com.messon.student.app.data.remote.dto.ComplaintVoteResDto
import com.messon.student.app.data.remote.dto.CreateComplaintReqDto
import com.messon.student.app.data.remote.dto.FetchComplaintRespDto
import com.messon.student.app.domain.dataModels.Complaint
import com.messon.student.app.domain.dataModels.ComplaintStatus
import com.messon.student.app.domain.dataModels.ComplaintVoteReq
import com.messon.student.app.domain.dataModels.ComplaintVoteRes
import com.messon.student.app.domain.dataModels.CreateComplaintReq
import com.messon.student.app.domain.dataModels.FetchComplaintsRes
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun CreateComplaintReq.toDto(): CreateComplaintReqDto {
    return CreateComplaintReqDto(
        subject = title,
        description = description
    )
}

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

fun ComplaintVoteReq.toDto(): ComplaintVoteReqDto {
    return ComplaintVoteReqDto(
        complaintId = complaintId,
        voteType = voteType
    )
}

fun ComplaintVoteResDto.toDomain(): ComplaintVoteRes {
    return ComplaintVoteRes(
        likeCount = likeCount,
        dislikeCount = dislikeCount,
        userVote=userVote
    )
}

// Manual date formatter that works in Kotlin Multiplatform
fun String.formatDate(): String {
    return try {
        val instant = Instant.parse(this) // Parse ISO 8601: 2026-06-21T12:33:49.116796Z
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        // Format manually
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
        // If parsing fails, try simpler format
        try {
            // Try to extract date from string: 2026-06-21T12:33:49.116796Z
            val parts = this.split("T")
            if (parts.size == 2) {
                val datePart = parts[0] // 2026-06-21
                val timePart = parts[1].substring(0, 5) // 12:33
                "$datePart $timePart"
            } else {
                this // Return original if all parsing fails
            }
        } catch (e2: Exception) {
            this // Return original if all parsing fails
        }
    }
}
