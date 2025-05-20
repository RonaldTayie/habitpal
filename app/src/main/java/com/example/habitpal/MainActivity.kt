package com.example.habitpal

import MainScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.habitpal.ui.theme.HabitPalTheme
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel
import com.example.habitpal.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {

    private lateinit var habitViewModel: HabitViewModel
    private lateinit var habitLogViewModel: HabitLogViewModel
    private lateinit var habitStreakViewModel: HabitStreakViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as HabitPalApp
        habitViewModel = HabitViewModel(app.habitRepository)
        habitLogViewModel = HabitLogViewModel(app.habitLogRepository)
        habitStreakViewModel = HabitStreakViewModel(app.habitLogRepository)

        enableEdgeToEdge()
        setContent {
            HabitPalTheme {
                MainScreen(habitVM=habitViewModel,habitLogVM=habitLogViewModel, streakVM = habitStreakViewModel)
            }
        }
    }
}
