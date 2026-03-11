package com.messon.student.app.presentation.mapper
import com.messon.student.app.data.remote.dto.MealAvailabilityDto
import com.messon.student.app.data.remote.dto.MenuResDto
import com.messon.student.app.domain.dataModels.MealAvailability
import com.messon.student.app.domain.dataModels.Menu

fun MenuResDto.toDomain():Menu{
    return Menu(
        day = day,
        mealSlot = mealSlot,
        mealType = mealType,
        dishes = dishes
    )
}

fun MealAvailabilityDto.toDomain(): MealAvailability {
    return MealAvailability(
        day = day,
        mealSlot=mealSlot,
        isVegAvailable = isVegAvailable,
        isNonVegAvailable = isNonVegAvailable
    )
}