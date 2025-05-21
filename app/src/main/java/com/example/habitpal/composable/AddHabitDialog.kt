import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.habitpal.composable.FrequencyDropdown
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.viewmodel.HabitViewModel

@Composable
fun AddHabitDialog(viewModel: HabitViewModel) {
    val state by viewModel.state.collectAsState()

    AlertDialog(
        onDismissRequest = {
            viewModel.onEvent(HabitEvent.HideDialog)
        },
        confirmButton = {
            TextButton(onClick = {
                if(viewModel.state.value.isEditingHabit){
                    viewModel.onEvent(HabitEvent.EditHabit)
                }else{
                    viewModel.onEvent(HabitEvent.SaveHabit)
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = { viewModel.onEvent(HabitEvent.HideDialog) }) {
                Text("Cancel")
            }
        },
        title = {
            if(state.isEditingHabit){
                Text("Edit Habit")
            }else{
                Text("New Habit")
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = { viewModel.onEvent(HabitEvent.SetTitle(it)) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.onEvent(HabitEvent.SetDescription(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                FrequencyDropdown(
                    selected = state.frequency,
                    onSelected = { viewModel.onEvent(HabitEvent.SetFrequency(it)) }
                )
            }
        }
    )
}
