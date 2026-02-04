package com.messon.student.app.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity
data class CouponEntity(
    @PrimaryKey
    val couponId: String,
    val uid: String,
    val date: LocalDate,
    val mealSlot: String,
    val expireAt: Long?,
    val signature:String,
    val usedAt: Int?,
    )