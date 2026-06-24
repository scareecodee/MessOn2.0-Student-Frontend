package com.messon.student.app.data.remote.dto

import com.messon.student.app.utils.UserInteraction
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
data class CreateComplaintReqDto(
    @SerialName("subject")
    val subject: String,

    @SerialName("description")
    val description: String
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
data class ComplaintVoteReqDto(
    @SerialName("complaint_id")
    val complaintId: String,

    @SerialName("vote_type")
    val voteType: String
)

@Serializable
data class ComplaintVoteResDto(
    @SerialName("likes_count")
    val likeCount: Int,

    @SerialName("dislikes_count")
    val dislikeCount: Int,

    @SerialName("user_vote")
    val userVote: String? = null
)