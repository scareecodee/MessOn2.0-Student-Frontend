package com.messon.student.app.utils

import com.messon.student.app.domain.dataModels.Menu


fun getDish(
    menu: List<Menu>,
    day: String,
    slot: String,
    diet: Int
): String {

    val mealType =
        if (diet == 0) MealType.VEG.value
        else MealType.NON_VEG.value

    return menu.firstOrNull {
        it.day.lowercase().startsWith(day.lowercase()) &&
                it.mealSlot.equals(slot, ignoreCase = true) &&
                it.mealType.lowercase() == mealType.lowercase()
    }?.dishes ?: "Not available"
}

fun convertDay(day: String): WeekDay {
    return when (day) {
        "Mon" -> WeekDay.MONDAY
        "Tue" -> WeekDay.TUESDAY
        "Wed" -> WeekDay.WEDNESDAY
        "Thu" -> WeekDay.THURSDAY
        "Fri" -> WeekDay.FRIDAY
        "Sat" -> WeekDay.SATURDAY
        else -> WeekDay.SUNDAY
    }
}