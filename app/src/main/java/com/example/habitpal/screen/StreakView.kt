package com.example.habitpal.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.habitpal.composable.HabitLineChart
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StreakView(
    habitId: Long,
    viewModel: HabitStreakViewModel,
    logVM: HabitLogViewModel,
    padding: PaddingValues,
) {
    val streak by viewModel.streak.collectAsState()
    val logs by logVM.state.collectAsState()


    LaunchedEffect(habitId) {
        viewModel.loadStreak(habitId)
        logVM.loadHabitLogs(habitId)
    }

    Column {
        Box(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min).padding(padding)){
            HabitLineChart(logs.habitLog)
        }
        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Current Streak: $streak days", style = MaterialTheme.typography.headlineLarge)
            }

        }

    }


}
