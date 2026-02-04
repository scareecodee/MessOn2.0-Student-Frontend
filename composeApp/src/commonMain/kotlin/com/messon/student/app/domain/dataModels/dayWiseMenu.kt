package com.messon.student.app.domain.dataModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DayWiseMenu(
    var isExpanded: MutableState<Boolean> = mutableStateOf(false),
    val shortDay: String,
    val day: String,
    val breakfastDishes: List<String>,
    val lunchDishes: List<String>,
    val dinnerDishes: List<String>
)
