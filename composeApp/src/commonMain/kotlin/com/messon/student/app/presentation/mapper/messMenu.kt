package com.messon.student.app.presentation.mapper
import com.messon.student.app.data.remote.dto.MenuResDto
import com.messon.student.app.domain.dataModels.Menu

fun MenuResDto.toDomain():Menu{
    return Menu(
        day = day,
        mealSlot = mealSlot,
        mealType = mealType,
        dishes = dishes
    )
}

