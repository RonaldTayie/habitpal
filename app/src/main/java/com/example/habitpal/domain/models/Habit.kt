package com.example.habitpal.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.habitpal.domain.enums.Frequency

@Entity(tableName = "habit")
data class Habit(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val title: String,
    val description: String,
    val frequency: Frequency,
    val isArchived: Boolean = false,
    val groupId: Long? = null

)
