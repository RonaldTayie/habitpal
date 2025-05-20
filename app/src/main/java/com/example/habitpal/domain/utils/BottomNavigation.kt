package com.example.habitpal.domain.utils

sealed class BottomNavItem(val route: String, val label: String) {
    object Habits : BottomNavItem("habit_list", "Habits")
    object Add : BottomNavItem("add_habit", "Add")
}