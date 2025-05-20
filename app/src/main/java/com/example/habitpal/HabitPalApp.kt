package com.example.habitpal

import android.app.Application
import com.example.habitpal.domain.database.DatabaseProvider
import com.example.habitpal.domain.repositories.HabitLogRepository
import com.example.habitpal.domain.repositories.HabitRepository

class HabitPalApp : Application() {

    lateinit var habitRepository: HabitRepository
        private set

    lateinit var habitLogRepository: HabitLogRepository
        private set


    override fun onCreate() {
        super.onCreate()

        val db = DatabaseProvider.getInstance(this)

        habitRepository = HabitRepository(db.habitDao())
        habitLogRepository = HabitLogRepository(db.habitLogDao())
    }
}
