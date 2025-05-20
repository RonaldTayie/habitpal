package com.example.habitpal.state;
import com.example.habitpal.domain.models.HabitLog;

data class HabitLogState (
    val habitLog: List<HabitLog> = emptyList()
)
