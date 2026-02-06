package com.messon.student.app.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    primaryKeys = ["day", "mealSlot"]
)
data class MessMenuEntity(
    val menuId: String,
    val date: String,
    val day: String,
    val mealSlot: String,
    val dishesName: String,
)
