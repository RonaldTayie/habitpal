package com.example.habitpal.composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.habitpal.domain.enums.Frequency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrequencyDropdown(
    selected: Frequency,
    onSelected: (Frequency) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected.name.uppercase().replaceFirstChar { it.uppercase() },
            onValueChange = {},
            readOnly = true,
            label = { Text("Frequency") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Frequency.entries.forEach { frequency ->
                DropdownMenuItem(
                    text = {
                        Text(frequency.name.lowercase().replaceFirstChar { it.uppercase() })
                    },
                    onClick = {
                        onSelected(frequency)
                        expanded = false
                    }
                )
            }
        }
    }
}
