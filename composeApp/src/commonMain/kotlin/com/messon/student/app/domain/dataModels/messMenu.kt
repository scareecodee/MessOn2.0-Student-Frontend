package com.messon.student.app.domain.dataModels

data class Menu(
    val day: String,
    val mealSlot: String,
    val mealType: String,
    val dishes: String?
)


data class MenuItem(
    val veg: String = "",
    val nonVeg: String = ""
)

data class DayMenu(
    val breakfast: MenuItem = MenuItem(),
    val lunch: MenuItem = MenuItem(),
    val dinner: MenuItem = MenuItem()
)

data class  MealAvailability(
    val day: String,
    val mealSlot: String,
    val isVegAvailable: Boolean,
    val isNonVegAvailable: Boolean
)
