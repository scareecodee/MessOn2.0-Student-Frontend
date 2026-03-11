package com.messon.student.app.utils

import com.messon.student.app.domain.dataModels.Menu
import com.messon.student.app.presentation.ui.screens.mess.DayMenu
import com.messon.student.app.presentation.ui.screens.mess.MealMenu

fun List<Menu>.toDayMenuMap(): Map<String, DayMenu> {

    val map = mutableMapOf<String, DayMenu>()

    WeekDay.entries.forEach { day ->

        val breakfastVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.BREAKFAST.value && it.mealType == MealType.VEG.value
        }?.dishes ?: ""

        val breakfastNonVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.BREAKFAST.value && it.mealType == MealType.NON_VEG.value
        }?.dishes ?: ""

        val lunchVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.LUNCH.value && it.mealType == MealType.VEG.value
        }?.dishes ?: ""

        val lunchNonVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.LUNCH.value && it.mealType == MealType.NON_VEG.value
        }?.dishes ?: ""

        val dinnerVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.DINNER.value && it.mealType == MealType.VEG.value
        }?.dishes ?: ""

        val dinnerNonVeg = find {
            it.day == day.value && it.mealSlot == MealSlot.DINNER.value && it.mealType ==MealType.NON_VEG.value
        }?.dishes ?: ""

        map[day.value] = DayMenu(
            breakfast = MealMenu(breakfastVeg, breakfastNonVeg),
            lunch = MealMenu(lunchVeg, lunchNonVeg),
            dinner = MealMenu(dinnerVeg, dinnerNonVeg)
        )
    }

    return map
}