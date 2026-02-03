package com.messon.app.data.remote.dto
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.datetime.LocalDate

@Serializable
data class CouponDetailDto(

    @SerialName("coupon_id")
    val couponId: String,

    @SerialName("user_id")
    val uid: String,

    @SerialName("date")
    val date: LocalDate,

    @SerialName("meal")
    val mealSlot: String,

    @SerialName("used_at")
    val usedAt: Int?,

    @SerialName("expires_at")
    val expireAt: Long?,

    @SerialName("signature")
    val signature:String

)

