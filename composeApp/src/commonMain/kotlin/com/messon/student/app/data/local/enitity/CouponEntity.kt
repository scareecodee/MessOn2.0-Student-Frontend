package com.messon.student.app.data.local.enitity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.messon.student.app.utils.MealType
import kotlinx.datetime.LocalDate

@Entity(
    primaryKeys = ["date", "mealSlot"]
)
data class CouponEntity(
    val couponId: String,
    val uid: String,
    val date: LocalDate,
    val mealSlot: String,
    val expireAt: Long?,
    val signature:String,
    val usedAt: Int?,
    val mealType: String
)