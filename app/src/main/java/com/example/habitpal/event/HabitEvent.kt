package com.example.habitpal.event
import com.example.habitpal.domain.enums.Frequency

sealed interface HabitEvent {
    data object SaveHabit : HabitEvent
    data object EditHabit: HabitEvent
    data object DeleteHabit: HabitEvent
    data class SetTitle(val title:String): HabitEvent
    data class SetDescription(val description:String):HabitEvent
    data class SetFrequency(val frequency: Frequency): HabitEvent
    data class SetArchived(val isArchived: Boolean): HabitEvent
    data object ShowDialog: HabitEvent
    data object HideDialog: HabitEvent
    data class SetViewFrequency(val frequency: Frequency): HabitEvent
}