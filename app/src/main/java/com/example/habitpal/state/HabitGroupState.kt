package com.example.habitpal.state

import com.example.habitpal.domain.models.HabitGroup

data class HabitGroupState(
    val id:Long = 0,
    val groups:List<HabitGroup> = emptyList(),
    val name:String = "",
    val isGroupDialogOpen: Boolean = false,
    val targetGroup: HabitGroup? = null
)
