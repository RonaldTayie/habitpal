import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habitpal.composable.ConfirmDeleteDialog
import com.example.habitpal.composable.HabitGroupDialog
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.event.HabitGroupEvent
import com.example.habitpal.screen.HabitListScreen
import com.example.habitpal.screen.StreakView
import com.example.habitpal.viewmodel.HabitGroupViewModel
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel
import com.example.habitpal.viewmodel.HabitViewModel
import kotlinx.coroutines.flow.update

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    habitVM: HabitViewModel,
    streakVM: HabitStreakViewModel,
    habitLogVM: HabitLogViewModel,
    habitGroupVM: HabitGroupViewModel
) {

    val state = habitVM.state.collectAsState()
    val habitGroupState = habitGroupVM.state.collectAsState()
    val success by habitLogVM.logSuccess.collectAsState()

    var expandedGroupMenu by remember { mutableStateOf(false) }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (currentRoute == "streak_view/{habitId}") {
                        IconButton(onClick = {
                            habitLogVM.clearState()
                            navController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Add")
                        }
                    }
                },
                title = {
                    Text("HabitPal")
                },
                actions = {
                    if (currentRoute == "habit_list") {
                        Box {
                            IconButton(onClick = { expandedGroupMenu = !expandedGroupMenu }) {
                                Icon(Icons.AutoMirrored.Default.List, contentDescription = "Filter")
                            }
                            DropdownMenu(
                                expanded = expandedGroupMenu,
                                onDismissRequest = { expandedGroupMenu = false },
                            ) {
                                DropdownMenuItem(
                                    text = { Text("All") },
                                    onClick = { habitVM.loadHabits() }
                                )
                                habitGroupState.value.groups.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                option.name,
                                                modifier = Modifier.combinedClickable(
                                                    onClick = {
                                                        expandedGroupMenu=false
                                                        habitVM.getGroupHabits(option.id)
                                                    },
                                                    onLongClick = {
                                                        habitGroupVM.state.update { it.copy(
                                                            isGroupDialogOpen = true,
                                                            targetGroup=option,
                                                            id = option.id,
                                                            name = option.name
                                                        )}
                                                        habitGroupVM.onEvent(event=HabitGroupEvent.OpenGroupDialog)
                                                    }
                                                ).fillMaxSize(),)
                                        },
                                        onClick = {
                                            expandedGroupMenu=false
                                            habitVM.getGroupHabits(option.id)
                                        }
                                    )
                                }
                            }
                        }

                        IconButton(onClick = {
                            habitGroupVM.onEvent(event=HabitGroupEvent.OpenGroupDialog)
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Create Group")
                        }

                    }else {
                        Button(
                            onClick = { habitLogVM.logToday(navController.context,
                                state.value.targetHabit?.id ?: 0
                            ) },
                            enabled = success != true // Disable if already logged
                        ) {
                            Text(
                                when (success) {
                                    null -> "Log Today"
                                    true -> "Already Logged"
                                    false -> "Logged"
                                }
                            )
                        }
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    habitVM.onEvent(event = HabitEvent.ShowDialog)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = "habit_list",
                modifier = Modifier.fillMaxSize()
            ) {
                composable ("habit_list"){
                    HabitListScreen(
                        habitViewModel = habitVM,
                        habitGroupVM = habitGroupVM,
                        padding=innerPadding,
                        onHabitSelected = { habit ->
                            habitVM.state.update { it.copy(
                                targetHabit = habit
                            )}
                            navController.navigate("streak_view/${habit.id}")
                        }
                    )
                }
                composable(
                    route = "streak_view/{habitId}",
                    arguments = listOf(navArgument("habitId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val habitId = backStackEntry.arguments?.getLong("habitId") ?: return@composable
                    StreakView(habitId = habitId, viewModel = streakVM, logVM = habitLogVM,padding=innerPadding)
                }
            }

            if (state.value.isAddingHabit) {
                AddHabitDialog(viewModel = habitVM, groupVM = habitGroupVM)
            }

            if (state.value.isEditingHabit) {
                AddHabitDialog(viewModel = habitVM,groupVM=habitGroupVM)
            }
            if(state.value.isDeletingHabit && state.value.targetHabit != null){
                ConfirmDeleteDialog(viewModel = habitVM)
            }

            if(habitGroupState.value.isGroupDialogOpen){
                HabitGroupDialog(habitGroupVM = habitGroupVM)
            }
        }
    )
}

