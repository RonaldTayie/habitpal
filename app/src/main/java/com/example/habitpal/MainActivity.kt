package com.example.habitpal

import MainScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.habitpal.ui.theme.HabitPalTheme
import com.example.habitpal.viewmodel.HabitGroupViewModel
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel
import com.example.habitpal.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {

    private lateinit var habitViewModel: HabitViewModel
    private lateinit var habitLogViewModel: HabitLogViewModel
    private lateinit var habitStreakViewModel: HabitStreakViewModel
    private lateinit var habitGroupViewModel: HabitGroupViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as HabitPalApp
        habitViewModel = HabitViewModel(app.habitRepository)
        habitLogViewModel = HabitLogViewModel(app.habitLogRepository,habitRepository = app.habitRepository)
        habitStreakViewModel = HabitStreakViewModel(app.habitLogRepository)
        habitGroupViewModel = HabitGroupViewModel(app.habitGroupRepository)

        setContent {
            HabitPalTheme {
                MainScreen(habitVM=habitViewModel,habitLogVM=habitLogViewModel, streakVM = habitStreakViewModel, habitGroupVM = habitGroupViewModel)
            }
        }
    }
}
