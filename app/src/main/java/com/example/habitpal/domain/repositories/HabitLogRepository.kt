package com.example.habitpal.domain.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitpal.domain.daos.HabitLogDao
import com.example.habitpal.domain.models.HabitLog
import java.time.LocalDate


class HabitLogRepository(
    private val logDao: HabitLogDao
) {

    suspend fun getHabitLogs(id:Long): List<HabitLog> {
        return logDao.getAllLogsForHabit(id)
    }

    suspend fun getDateLog(id:Long, date:LocalDate): HabitLog? {
        return logDao.getLogForDate(id,date)
    }

    suspend fun insertLog(log: HabitLog):Long {
        return logDao.insert(log)
    }

    suspend fun getSortedLogs(id: Long): List<HabitLog> {
        return getHabitLogs(id).sortedByDescending { it.date }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun logToday(habitId: Long): Boolean {
        val today = LocalDate.now()
        val exists = getDateLog(habitId, today)
        return if (exists == null) {
            insertLog(HabitLog(habitId = habitId, date = today))
            true
        } else {
            false
        }
    }

}