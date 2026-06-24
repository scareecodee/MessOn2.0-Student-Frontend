package com.messon.student.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitRatingReqDto(
    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("rating")
    val rating: Int
)
