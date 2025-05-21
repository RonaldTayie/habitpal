package com.example.habitpal.domain.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.domain.models.HabitGroup

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit WHERE isArchived=0;")
    suspend fun getAll(): List<Habit>

    @Query("SELECT * FROM habit WHERE id=:id LIMIT 1")
    suspend fun getHabit(id: Long): Habit?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(habit: Habit): Long

    @Update
    suspend fun update(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: HabitGroup): Long

    @Query("SELECT * FROM habit WHERE groupId=:id")
    suspend fun getAllGroups(id:Long): List<Habit>

}