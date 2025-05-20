package com.example.habitpal.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habitpal.domain.models.HabitLog
import java.time.LocalDate

@Dao
interface HabitLogDao {
    @Query("SELECT * FROM habit_log WHERE habitId = :habitId ORDER BY date DESC")
    suspend fun getAllLogsForHabit(habitId: Long): List<HabitLog>

    @Query("SELECT * FROM habit_log WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getLogForDate(habitId: Long, date: LocalDate): HabitLog?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(log: HabitLog): Long
}