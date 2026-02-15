package com.messon.student.app.domain.dataModels


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetail(
    val uid: String,
    val date: LocalDate,
    val mealSlot: String,
    val usedAt: Int?,
    val expiredAt: Long?,
    val couponId: String,
    val signature: String,
    val mealType: String
)
