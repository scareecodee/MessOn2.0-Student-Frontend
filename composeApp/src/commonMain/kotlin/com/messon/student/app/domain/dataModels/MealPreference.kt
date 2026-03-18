package com.messon.student.app.domain.dataModels
import com.messon.student.app.utils.MealType

data class MealPref(
    val day: String,
    val mealSlot: String,
    val mealType: String?,
)

data class PrefSetupData(

    val day: String,
    val mealSlot: String,
    val vegDish: String?,
    val nonVegDish: String?,
    val vegAvailability: Boolean,
    val nonVegAvailability: Boolean,
    val isCompulsory: Boolean,
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
