package com.messon.app.domain.dataModels

import kotlinx.datetime.LocalDate

data class CouponDetail(
    val uid: String,
     val date: LocalDate,
     val mealSlot: String,
     val usedAt:Int?,
     val expiredAt: Long?,
     val couponId: String,
    val signature:String
)
