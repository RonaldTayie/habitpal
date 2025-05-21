package com.example.habitpal.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.habitpal.event.HabitGroupEvent
import com.example.habitpal.viewmodel.HabitGroupViewModel

@Composable
fun HabitGroupDialog(habitGroupVM: HabitGroupViewModel) {
    val state by habitGroupVM.state.collectAsState()

    AlertDialog(
        onDismissRequest={
            habitGroupVM.onEvent(HabitGroupEvent.CloseGroupDialog)
        },
        confirmButton = {
            TextButton(onClick = {
                if(state.targetGroup!==null){
                    habitGroupVM.onEvent(HabitGroupEvent.UpdateHabitGroup)
                }else{
                    habitGroupVM.onEvent(HabitGroupEvent.SaveHabitGroup)
                }
                habitGroupVM.onEvent(HabitGroupEvent.CloseGroupDialog)
            }) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                habitGroupVM.onEvent(HabitGroupEvent.CloseGroupDialog)
            }) {
                Text("Cancel")
            }
        },
        title = { if(state.targetGroup!=null){
            Text("Edit Group")
        }else{
            Text("Create Group")
        } },
        text = {
            Column {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { habitGroupVM.onEvent(HabitGroupEvent.SetGroupName(it)) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}


