package com.example.habitpal.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.habitpal.domain.enums.Frequency

@Composable
fun FrequencyDropdown(
    selected: Frequency,
    onSelected: (Frequency) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selected.name.lowercase().replaceFirstChar { it.uppercase() },
            onValueChange = {},
            label = { Text("Frequency") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth().clickable { expanded = true }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Frequency.entries.forEach { freq ->
                DropdownMenuItem(
                    text = { Text(freq.name.lowercase().replaceFirstChar { it.uppercase() }) },
                    onClick = {
                        onSelected(freq)
                        expanded = false
                    }
                )
            }
        }
    }
}
