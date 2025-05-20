package com.example.habitpal.domain.repositories

import com.example.habitpal.domain.daos.HabitDao
import com.example.habitpal.domain.models.Habit

class HabitRepository(
    private val habitDao: HabitDao
) {

    suspend fun getAllHabits():List<Habit> {
        return habitDao.getAll()
    }

    suspend fun getHabit(id:Long): Habit? {
        return habitDao.getHabit(id)
    }

    suspend fun insertHabit(habit:Habit):Long {
        return habitDao.insert(habit)
    }

    suspend fun updateHabit(habit:Habit) {
        habitDao.update(habit)
    }

    suspend fun deleteHabit(habit: Habit){
        habitDao.delete(habit)
    }

}