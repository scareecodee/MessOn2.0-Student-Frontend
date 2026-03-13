package com.messon.student.app.presentation.mapper

import com.messon.student.app.data.remote.dto.PrefSetupDto
import com.messon.student.app.domain.dataModels.PrefSetupData

fun PrefSetupDto.toDomain(): PrefSetupData{
    return PrefSetupData(
        day = day,
        mealSlot = mealSlot,
        vegDish = vegDish,
        nonVegDish =nonVegDish,
        vegAvailability = vegAvailability,
        nonVegAvailability =nonVegAvailability,
        isCompulsory = isCompulsory
    )
}