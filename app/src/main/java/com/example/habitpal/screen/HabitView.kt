package com.example.habitpal.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.habitpal.composable.HabitCard
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.viewmodel.HabitGroupViewModel
import com.example.habitpal.viewmodel.HabitViewModel

@Composable
fun HabitListScreen(
    habitViewModel: HabitViewModel,
    habitGroupVM: HabitGroupViewModel,
    padding: PaddingValues,
    onHabitSelected: (Habit) -> Unit = {}
) {
    val state by habitViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        habitGroupVM.loadGroups()
        habitViewModel.loadHabits()
    }

    if (state.habits.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("No habits found.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items=state.habits,
                itemContent = {habit-> HabitCard(
                        habitViewModel = habitViewModel,
                        habit = habit,
                        onHabitSelected = onHabitSelected
                    )
                }
            )
        }
    }
}
