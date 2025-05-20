package com.example.habitpal.state
import com.example.habitpal.domain.enums.Frequency
import com.example.habitpal.domain.models.Habit

data class HabitState (
    val habits: List<Habit> = emptyList(),
    val title: String = "",
    val description: String ="",
    val frequency: Frequency = Frequency.DAILY,
    val isArchived: Boolean = false,
    val isAddingHabit: Boolean = false,
    val viewFrequency: Frequency = Frequency.DAILY
)