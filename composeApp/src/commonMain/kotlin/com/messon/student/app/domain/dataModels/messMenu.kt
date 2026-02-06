package com.messon.student.app.domain.dataModels


data class EachDayMenu(
    val breakfast: EachMealSlotMenu,
    val lunch: EachMealSlotMenu,
    val dinner: EachMealSlotMenu
)


data class  EachMealSlotMenu(
    val menuId: String,
    val date: String ,
    val day: String,
    val mealSlot: String,
    val dishesName: String,
)
