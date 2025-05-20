import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.screen.HabitListScreen
import com.example.habitpal.screen.StreakView
import com.example.habitpal.viewmodel.HabitLogViewModel
import com.example.habitpal.viewmodel.HabitStreakViewModel
import com.example.habitpal.viewmodel.HabitViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    habitVM: HabitViewModel,
    streakVM: HabitStreakViewModel,
    habitLogVM: HabitLogViewModel
) {
    val state = habitVM.state.collectAsState()

    val navController = rememberNavController()

    Scaffold(
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
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                composable ("habit_list"){
                    HabitListScreen(
                        habitViewModel = habitVM,
                        padding=innerPadding,
                        onHabitSelected = { habit ->
                            navController.navigate("streak_view/${habit.id}")
                        }
                    )
                }
                composable(
                    route = "streak_view/{habitId}",
                    arguments = listOf(navArgument("habitId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val habitId = backStackEntry.arguments?.getLong("habitId") ?: return@composable
                    StreakView(habitId = habitId, viewModel = streakVM,navController=navController, logVM = habitLogVM)
                }
            }

            if (state.value.isAddingHabit) {
                AddHabitDialog(viewModel = habitVM)
            }
        }
    )
}

