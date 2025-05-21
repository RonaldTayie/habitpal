import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habitpal.composable.ConfirmDeleteDialog
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.screen.HabitListScreen
import com.example.habitpal.screen.StreakView
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel
import com.example.habitpal.viewmodel.HabitViewModel
import kotlinx.coroutines.flow.update

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    habitVM: HabitViewModel,
    streakVM: HabitStreakViewModel,
    habitLogVM: HabitLogViewModel
) {

    val state = habitVM.state.collectAsState()
    val success by habitLogVM.logSuccess.collectAsState()

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(

        modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues()),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (currentRoute == "streak_view/{habitId}") {
                        IconButton(onClick = {
                            habitLogVM.clearState()
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Add")
                        }
                    }
                },
                title = {
                    Text("HabitPal")
                },
                actions = {
                    if (currentRoute == "streak_view/{habitId}") {
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
                AddHabitDialog(viewModel = habitVM)
            }

            if (state.value.isEditingHabit) {
                AddHabitDialog(viewModel = habitVM)
            }
            if(state.value.isDeletingHabit && state.value.targetHabit != null){
                ConfirmDeleteDialog(viewModel = habitVM)
            }
        }
    )
}

