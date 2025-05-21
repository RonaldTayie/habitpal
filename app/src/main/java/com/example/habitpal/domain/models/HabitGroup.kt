package com.example.habitpal.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_group")
data class HabitGroup(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)