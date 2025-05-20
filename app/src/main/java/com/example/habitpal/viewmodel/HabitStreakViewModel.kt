package com.example.habitpal.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitpal.domain.repositories.HabitLogRepository
import com.example.habitpal.domain.utils.calculateStreak
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitStreakViewModel(
    private val logRepo: HabitLogRepository
) : ViewModel() {

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadStreak(habitId: Long) {
        viewModelScope.launch {
            val logs = logRepo.getSortedLogs(habitId)
            _streak.value = calculateStreak(logs)
        }
    }
}
