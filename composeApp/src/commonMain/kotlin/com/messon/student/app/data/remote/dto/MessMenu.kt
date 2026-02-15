package com.messon.student.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class EachDayMenuDto(

    @SerialName("breakfast")
    var breakfast: EachMealSlotMenuDto,

    @SerialName("lunch")
    var lunch: EachMealSlotMenuDto,

    @SerialName("dinner")
    var dinner: EachMealSlotMenuDto,

    )

@Serializable

data class  EachMealSlotMenuDto(
    @SerialName("menu_id")
    val menuId: String,

    @SerialName("day")
    val day: String,

    @SerialName("meal_type")
    val mealSlot: String,

    @SerialName("menu_text")
    val dishesName: String,
)
