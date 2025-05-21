package com.example.habitpal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitpal.domain.models.HabitGroup
import com.example.habitpal.domain.repositories.HabitGroupRepository
import com.example.habitpal.event.HabitGroupEvent
import com.example.habitpal.state.HabitGroupState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HabitGroupViewModel(
    private val groupRepository: HabitGroupRepository
): ViewModel() {

    private val _state = MutableStateFlow(HabitGroupState())
    val state = _state

    suspend fun loadGroups(){
        val groups = groupRepository.getAllGroups()
        _state.update { it.copy(
            groups = groups
        ) }
    }

    fun getGroup(id:Long?): HabitGroup? {
        return _state.value.groups.find { g->g.id==id }
    }

    fun onEvent(event: HabitGroupEvent){
        when(event){
            is HabitGroupEvent.DeleteHabitGroup -> {
                val group = HabitGroup(id = _state.value.id, name = _state.value.name)
                viewModelScope.launch {
                    groupRepository.deleteGroup(group)
                    loadGroups()
                }
            }
            is HabitGroupEvent.SaveHabitGroup -> {
                val group = HabitGroup(
                    name = _state.value.name
                )
                viewModelScope.launch {
                    groupRepository.insertGroup(group)
                    loadGroups()
                }
                state.update { it.copy(
                    name = "",
                    id=1,
                    targetGroup = null
                )}
            }
            is HabitGroupEvent.UpdateHabitGroup->{
                val group = HabitGroup(id = _state.value.id, name = _state.value.name)
                viewModelScope.launch {
                    groupRepository.updateGroup(group)
                    loadGroups()
                }
                state.update { it.copy(
                    name = "",
                    id=1,
                    targetGroup = null
                )}
            }
            is HabitGroupEvent.CloseGroupDialog->{
                _state.update { it.copy(
                    isGroupDialogOpen = false
                ) }
            }
            is HabitGroupEvent.OpenGroupDialog -> {
                _state.update { it.copy(
                    isGroupDialogOpen = true
                ) }
            }
            is HabitGroupEvent.SetGroupName -> {
                _state.update{ it.copy(
                    name = event.name
                )}
            }
        }
    }

    fun clearState() {
        state.update {
            it.copy(
                groups = emptyList(),
                name = "",
                isGroupDialogOpen = false,
                id = 0
            )
        }
        viewModelScope.launch {
            loadGroups()
        }
    }
}