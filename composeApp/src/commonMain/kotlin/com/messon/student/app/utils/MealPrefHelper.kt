package com.messon.student.app.utils
import com.messon.student.app.domain.dataModels.PrefSetupData


fun List<PrefSetupData>.toDayMenuMap(): Map<String, Map<MealSlot, PrefSetupData>> {
    return this.groupBy { dto ->
        when (dto.day.lowercase()) {
            WeekDay.MONDAY.value -> "Mon"
            WeekDay.TUESDAY.value -> "Tue"
            WeekDay.WEDNESDAY.value -> "Wed"
            WeekDay.THURSDAY.value -> "Thu"
            WeekDay.FRIDAY.value -> "Fri"
            WeekDay.SATURDAY.value -> "Sat"
            WeekDay.SUNDAY.value -> "Sun"
            else -> dto.day
        }
    }.mapValues { (_, meals) ->
        meals.associateBy { MealSlot.valueOf(it.mealSlot.uppercase()) }
    }
}