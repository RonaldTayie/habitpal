package com.example.habitpal.domain.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.habitpal.domain.models.HabitLog
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
fun calculateStreak(logs: List<HabitLog>): Int {
    val today = LocalDate.now()
    var current = today
    var streak = 0

    for (log in logs.sortedByDescending { it.date }) {
        if (log.date == current) {
            streak++
            current = current.minusDays(1)
        } else {
            break
        }
    }

    return streak
}
