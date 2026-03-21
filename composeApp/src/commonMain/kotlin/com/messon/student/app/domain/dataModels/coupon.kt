package com.messon.student.app.domain.dataModels


import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CouponDetail(
    val uid: String,
    val date: LocalDate,
    val mealSlot: String,
    val usedAt: Int?,
    val couponId: String,
    val messId: String,
    val collegeId: String,
    val signature: String,
    val mealType: String
)
