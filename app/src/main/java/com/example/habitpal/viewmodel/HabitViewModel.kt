package com.example.habitpal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitpal.domain.enums.Frequency
import com.example.habitpal.domain.models.Habit
import com.example.habitpal.domain.repositories.HabitRepository
import com.example.habitpal.event.HabitEvent
import com.example.habitpal.state.HabitState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitViewModel(
    private val habitRepository: HabitRepository
):ViewModel() {

    private val _state = MutableStateFlow(HabitState())

    val state = _state

    suspend fun loadHabits(){
        val h = habitRepository.getAllHabits()
        _state.update { it.copy(
            habits=h
        ) }
    }

    fun getHabits():List<Habit> {
        return _state.value.habits
    }

    fun onEvent(event:HabitEvent){
        when(event){
            is HabitEvent.DeleteHabit -> {
                viewModelScope.launch {
                    habitRepository.deleteHabit(event.habit)
                }
            }
            HabitEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingHabit = false
                ) }
            }
            HabitEvent.SaveHabit -> {
                val habit = Habit(
                    title = _state.value.title,
                    description = _state.value.description,
                    isArchived = _state.value.isArchived,
                    frequency = _state.value.frequency,
                )
                viewModelScope.launch {
                    habitRepository.insertHabit(habit)
                    loadHabits()
                }
                _state.update { it.copy(
                    isAddingHabit = false,
                    title = "",
                    description = "",
                    frequency = Frequency.DAILY,
                    isArchived = false,
                ) }

            }
            is HabitEvent.SetArchived -> {
                _state.update{it.copy(
                    isArchived = event.isArchived
                )}
            }
            is HabitEvent.SetDescription -> {
                _state.update{ it.copy(
                    description = event.description
                ) }
            }
            is HabitEvent.SetFrequency -> {
                _state.update { it.copy(
                    frequency = event.frequency
                ) }
            }
            is HabitEvent.SetTitle -> {
                _state.update { it.copy(
                    title = event.title
                ) }
            }
            is HabitEvent.SetViewFrequency -> {
                _state.update { it.copy(
                    viewFrequency = event.frequency
                ) }
            }
            HabitEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingHabit = true
                ) }
            }
        }
    }
}