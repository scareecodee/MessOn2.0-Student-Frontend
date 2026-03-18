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

fun String.toApiDay(): String {
    return when (this) {
        "Mon" -> WeekDay.MONDAY.value
        "Tue" -> WeekDay.TUESDAY.value
        "Wed" -> WeekDay.WEDNESDAY.value
        "Thu" -> WeekDay.THURSDAY.value
        "Fri" -> WeekDay.FRIDAY.value
        "Sat" -> WeekDay.SATURDAY.value
        "Sun" -> WeekDay.SUNDAY.value
        else -> this.lowercase()
    }
}


fun MealType.toApiValue(): String? {
    return when (this) {
        MealType.VEG -> MealType.VEG.value
        MealType.NON_VEG -> MealType.NON_VEG.value
        MealType.NONE -> null
    }
}