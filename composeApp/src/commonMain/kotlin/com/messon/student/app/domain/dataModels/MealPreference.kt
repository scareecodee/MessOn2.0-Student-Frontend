package com.messon.student.app.domain.dataModels

import com.messon.student.app.utils.MealType


data class DayPreference(
    val dayName: String,
    val breakfast: MealType,
    val lunch: MealType,
    val dinner: MealType
)