package com.messon.app.domain.dataModels

import kotlinx.datetime.LocalDate

data class CouponDetail(
    private val date: LocalDate,
    private val mealSlot: String,
    private val expired: Boolean,
    private val expiredAt: Long
)
