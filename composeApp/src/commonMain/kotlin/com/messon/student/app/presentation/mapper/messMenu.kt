package com.messon.student.app.presentation.mapper

import androidx.compose.ui.text.toLowerCase
import com.messon.student.app.data.local.enitity.MessMenuEntity
import com.messon.student.app.data.remote.dto.EachDayMenuDto
import com.messon.student.app.data.remote.dto.EachMealSlotMenuDto
import com.messon.student.app.domain.dataModels.EachDayMenu
import com.messon.student.app.domain.dataModels.EachMealSlotMenu


fun EachDayMenuDto.toDomain(): EachDayMenu{
    return EachDayMenu(
        breakfast=breakfast.toDomain(),
        lunch=lunch.toDomain(),
        dinner=dinner.toDomain()
    )
}

fun EachMealSlotMenuDto.toDomain(): EachMealSlotMenu{
    return EachMealSlotMenu(
        menuId = menuId,
        day = day.lowercase(),
        mealSlot =mealSlot.lowercase(),
        dishesName =dishesName,
    )
}


fun MessMenuEntity.toDomain(): EachMealSlotMenu{
    return EachMealSlotMenu(
        menuId = menuId,
        day = day.lowercase(),
        mealSlot =mealSlot.lowercase(),
        dishesName = dishesName,
    )
}


fun EachMealSlotMenu.toEntity(): MessMenuEntity{
    return MessMenuEntity(
        menuId = menuId,
        mealSlot= mealSlot.lowercase(),
        day=day.lowercase(),
        dishesName = dishesName
    )
}