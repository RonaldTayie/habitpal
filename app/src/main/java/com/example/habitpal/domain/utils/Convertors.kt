package com.example.habitpal.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.example.habitpal.domain.enums.Frequency
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromDate(date: LocalDate): String = date.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toDate(value: String): LocalDate = LocalDate.parse(value)

    @TypeConverter
    fun fromFrequency(frequency: Frequency): String = frequency.name

    @TypeConverter
    fun toFrequency(value: String): Frequency = Frequency.valueOf(value)
}
