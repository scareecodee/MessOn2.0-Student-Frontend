package com.messon.student.app.data.remote.dto
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.datetime.LocalDate

@Serializable
data class CouponDetailDto(

    @SerialName("coupon_id")
    val couponId: String,

    @SerialName("student_id")
    val uid: String,

    @SerialName("mess_id")
    val messId: String,

    @SerialName("college_id")
    val collegeId: String,

    @SerialName("date")
    val date: LocalDate,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("scanned_at")
    val usedAt: Int?,

    @SerialName("signature")
    val signature:String,

    @SerialName("meal_type")
    val mealType: String

)

