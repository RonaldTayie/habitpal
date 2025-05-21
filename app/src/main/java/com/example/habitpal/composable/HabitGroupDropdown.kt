package com.example.habitpal.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.habitpal.domain.models.HabitGroup
import com.example.habitpal.viewmodel.HabitGroupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitGroupDropdown(
    selected: HabitGroup?,
    onSelected: (HabitGroup?) -> Unit,
    groupVM: HabitGroupViewModel
) {
    var expanded by remember { mutableStateOf(false) }

    val state by groupVM.state.collectAsState()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected?.name?.replaceFirstChar { it.uppercase() } ?: "No Group",
            onValueChange = {},
            readOnly = true,
            label = { Text("Group") },
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
            DropdownMenuItem(
                text = { Text("No Group") },
                onClick = {
                    onSelected(null)
                    expanded = false
                }
            )

            state.groups.forEach { group ->
                DropdownMenuItem(
                    text = {
                        Text(group.name.replaceFirstChar { it.uppercase() })
                    },
                    onClick = {
                        onSelected(group)
                        expanded = false
                    }
                )
            }
        }
    }
}
