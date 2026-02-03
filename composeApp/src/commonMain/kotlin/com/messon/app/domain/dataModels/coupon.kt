package com.messon.app.domain.dataModels

import kotlinx.datetime.LocalDate

data class CouponDetail(
    private val date: LocalDate,
    private val mealSlot: String,
    private val usedAt:Int?,
    private val expiredAt: Long?,
    private val couponId: String
)
