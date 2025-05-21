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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.viewmodel.HabitViewModel
import kotlinx.coroutines.flow.update

@Composable
fun HabitListScreen(
    habitViewModel: HabitViewModel,
    padding: PaddingValues?,
    onHabitSelected: (Habit) -> Unit = {}
) {
    val state by habitViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        habitViewModel.loadHabits()
    }

    if (state.habits.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No habits found.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding ?: PaddingValues(0.dp))
        ) {
            items(
                items=state.habits,
                itemContent = {habit->Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onHabitSelected(habit) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), Arrangement.Absolute.SpaceBetween ) {
                        Column {
                            Text(
                                text = habit.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = habit.description,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Box(){
                            Row(){
                                AssistChip(
                                    onClick = {},
                                    label = { Text(habit.frequency.toString()) }
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                AssistChip(
                                    onClick = {
                                        habitViewModel.state.update { it.copy(
                                            id = habit.id,
                                            title = habit.title,
                                            isArchived = habit.isArchived,
                                            description = habit.description,
                                            isEditingHabit = true
                                        ) }
                                    },
                                    label = {
                                        Text("EDIT")
                                    }
                                )
                            }
                        }

                    }
                }}
            )
        }
    }
}
