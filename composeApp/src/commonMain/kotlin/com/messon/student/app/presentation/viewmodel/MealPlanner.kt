package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.messon.student.app.domain.dataModels.DayPreference
import com.messon.student.app.utils.MealType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MealPlannerViewModel : ViewModel() {

    private val days = listOf(
        "Monday","Tuesday","Wednesday",
        "Thursday","Friday","Saturday","Sunday"
    )

    private fun defaultWeeklyState(): Map<String, DayPreference> =
        days.mapIndexed { index, day ->
            val defaultType = if (index < 5) MealType.VEG else MealType.NONE
            day to DayPreference(day, defaultType, defaultType, defaultType)
        }.toMap()

    private val _weeklyState = MutableStateFlow(defaultWeeklyState())
    val weeklyState: StateFlow<Map<String, DayPreference>> = _weeklyState

    private val _activeDay = MutableStateFlow("Monday")
    val activeDay: StateFlow<String> = _activeDay

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved

    fun setActiveDay(day: String) {
        _activeDay.value = day
    }

    fun updateBreakfast(type: MealType) {
        val day = _activeDay.value
        val updated = _weeklyState.value.toMutableMap()
        updated[day] = updated[day]!!.copy(breakfast = type)
        _weeklyState.value = updated
        _isSaved.value = false
    }

    fun updateLunch(type: MealType) {
        val day = _activeDay.value
        val updated = _weeklyState.value.toMutableMap()
        updated[day] = updated[day]!!.copy(lunch = type)
        _weeklyState.value = updated
        _isSaved.value = false
    }

    fun updateDinner(type: MealType) {
        val day = _activeDay.value
        val updated = _weeklyState.value.toMutableMap()
        updated[day] = updated[day]!!.copy(dinner = type)
        _weeklyState.value = updated
        _isSaved.value = false
    }

    fun resetDay() {
        val day = _activeDay.value
        val index = days.indexOf(day)
        val defaultType = if (index < 5) MealType.VEG else MealType.NONE

        val updated = _weeklyState.value.toMutableMap()
        updated[day] =
            DayPreference(day, defaultType, defaultType, defaultType)

        _weeklyState.value = updated
        _isSaved.value = false
    }

    fun save() {
        _isSaved.value = true
    }



}

