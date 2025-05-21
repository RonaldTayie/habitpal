package com.example.habitpal.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.viewmodel.HabitViewModel

@Composable
fun ConfirmDeleteDialog (viewModel: HabitViewModel){
    val state by viewModel.state.collectAsState()

    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(HabitEvent.HideDialog)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.onEvent(HabitEvent.DeleteHabit)
                }
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.onEvent(HabitEvent.HideDialog) }) {
                Text("Cancel")
            }
        },
        title = {
            Text("Delete Habit")
        },
        text = {
            Column(){
                Text("Are you sure you want to delete this Habit?")
                Spacer(modifier = Modifier.height(5.dp))
                Text("Title:")
                Spacer(modifier = Modifier.height(5.dp))
                Text(state.targetHabit!!.title)
            }
        }
    )
}