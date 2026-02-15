package com.messon.student.app.data.local.enitity

import androidx.room.Entity


@Entity(
    primaryKeys = ["day", "mealSlot"]
)
data class MessMenuEntity(
    val menuId: String,
    val day: String,
    val mealSlot: String,
    val dishesName: String,
)
