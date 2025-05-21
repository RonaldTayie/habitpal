package com.example.habitpal.domain.repositories

import com.example.habitpal.domain.daos.HabitGroupDao
import com.example.habitpal.domain.models.HabitGroup

class HabitGroupRepository(
    private val groupDao: HabitGroupDao
) {

    suspend fun getAllGroups():List<HabitGroup> {
        return groupDao.getAllGroups()
    }

    suspend fun insertGroup(group: HabitGroup): Long {
        return groupDao.insertGroup(habit_group = group)
    }

    suspend fun updateGroup(group: HabitGroup) {
        return groupDao.updateGroup(group)
    }

    suspend fun deleteGroup(group: HabitGroup) {
        return groupDao.deleteGroup(group)
    }

}