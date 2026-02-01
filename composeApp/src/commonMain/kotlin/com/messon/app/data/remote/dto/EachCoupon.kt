package com.messon.app.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.datetime.LocalDate

@Serializable
data class CouponDetailDto(

    @SerialName("coupon_id")
    val couponId: String,

    @SerialName("uid")
    val uid: String,

    @SerialName("date")
    val date: LocalDate,

    @SerialName("mealSlot")
    val mealSlot: String,

    @SerialName("expired")
    val expired: Boolean,

    @SerialName("expire_at")
    val expireAt: Long,

)

@Serializable
data class GetCouponReqDto(

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("uid")
    val uid:String
)

