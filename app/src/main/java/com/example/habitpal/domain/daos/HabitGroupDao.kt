package com.example.habitpal.domain.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitpal.domain.models.HabitGroup

@Dao
interface HabitGroupDao {

    @Query("SELECT * FROM habit_group")
    suspend fun getAllGroups(): List<HabitGroup>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(habit_group: HabitGroup):Long

    @Update
    suspend fun updateGroup(habit_group: HabitGroup)

    @Delete
    suspend fun deleteGroup(habit_group: HabitGroup)
}