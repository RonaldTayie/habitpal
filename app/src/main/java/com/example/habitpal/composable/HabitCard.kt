package com.example.habitpal.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.viewmodel.HabitViewModel
import kotlinx.coroutines.flow.update

@Composable
fun HabitCard(
    habitViewModel: HabitViewModel,
    habit: Habit,
    onHabitSelected: (habit:Habit) -> Unit
){

    Card(
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
                    IconButton(onClick = {
                        habitViewModel.state.update { it.copy(
                            id = habit.id,
                            title = habit.title,
                            group = habit.groupId,
                            isArchived = habit.isArchived,
                            description = habit.description,
                            isEditingHabit = true
                        ) }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit"
                        )
                    }
                    IconButton(onClick = {
                        habitViewModel.state.update { it.copy(
                            targetHabit = habit,
                            id = habit.id,
                            isDeletingHabit = true
                        ) }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DeleteOutline,
                            contentDescription = "Delete"
                        )
                    }
                }
            }

        }
    }

}