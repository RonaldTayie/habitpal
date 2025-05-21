package com.example.habitpal.event

interface HabitGroupEvent {
    data object SaveHabitGroup: HabitGroupEvent
    data class SetGroupName(val name:String): HabitGroupEvent
    data object OpenGroupDialog: HabitGroupEvent
    data object CloseGroupDialog: HabitGroupEvent
    data object UpdateHabitGroup: HabitGroupEvent
    data object DeleteHabitGroup: HabitGroupEvent
}