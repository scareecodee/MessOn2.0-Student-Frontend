package com.messon.student.app.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrefSetupDto(

    @SerialName("day")
    val day: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("veg_dishes")
    val vegDish: String?,

    @SerialName("nonVeg_dishes")
    val nonVegDish: String?,

    @SerialName("veg_available")
    val vegAvailability: Boolean,

    @SerialName("nonVeg_available")
    val nonVegAvailability: Boolean,

    @SerialName("is_compulsory")
    val isCompulsory: Boolean,



)

@Serializable
data class MealPrefSaveReq(

    @SerialName("day")
    val day: String,

    @SerialName("meal_slot")
    val mealSlot: String,

    @SerialName("meal_type")
    val mealType: String?

)