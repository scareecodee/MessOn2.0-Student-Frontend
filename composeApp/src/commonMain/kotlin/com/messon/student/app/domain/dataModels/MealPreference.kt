package com.messon.student.app.domain.dataModels
import com.messon.student.app.utils.MealType

data class DayPreference(
    val dayName: String,
    val breakfast: MealType,
    val lunch: MealType,
    val dinner: MealType
)

data class DaySelection(
    var breakfast: MealType = MealType.NONE,
    var lunch: MealType = MealType.NONE,
    var dinner: MealType = MealType.NONE
) {
    val selectionCount: Int
        get() = listOf(breakfast, lunch, dinner).count { it != MealType.NONE }
}


data class DayModel(
    val id: String,
    val label: String,
    val letter: String
)
