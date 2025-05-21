package com.example.habitpal.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habitpal.domain.daos.HabitDao
import com.example.habitpal.domain.daos.HabitGroupDao
import com.example.habitpal.domain.daos.HabitLogDao
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.domain.models.HabitGroup
import com.example.habitpal.domain.models.HabitLog
import com.example.habitpal.domain.utils.Converters

@Database(
    entities = [Habit::class, HabitLog::class, HabitGroup::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitLogDao(): HabitLogDao
    abstract fun groupDao(): HabitGroupDao



}