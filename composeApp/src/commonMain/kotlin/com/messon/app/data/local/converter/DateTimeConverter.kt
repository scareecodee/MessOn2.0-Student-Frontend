package com.messon.app.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class DateTimeConverters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate = LocalDate.parse(dateString)
}
