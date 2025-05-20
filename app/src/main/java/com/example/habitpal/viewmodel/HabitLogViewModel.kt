package com.example.habitpal.viewmodel

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitpal.domain.repositories.HabitLogRepository
import com.example.habitpal.domain.utils.HabitReminderReceiver
import com.example.habitpal.state.HabitLogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitLogViewModel(
    private val habitLogRepository: HabitLogRepository
): ViewModel() {

    private val _state = MutableStateFlow(HabitLogState())
    val state = _state

    private val _logSuccess = MutableStateFlow<Boolean?>(null)
    val logSuccess = _logSuccess

    @RequiresApi(Build.VERSION_CODES.O)
    fun logToday(habitId: Long) {
        viewModelScope.launch {
            val success = habitLogRepository.logToday(habitId)
            _logSuccess.value = success
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadHabitLogs(habitId:Long){
        viewModelScope.launch {
            val logs = habitLogRepository.getHabitLogs(habitId)
            state.update { it.copy(
                habitLog = logs
            ) }
            val last = logs.findLast { log -> log.date==LocalDate.now() }
            if (last !=null){
                _logSuccess.value = true
            }
        }
    }

    fun clearState(){
        state.update { it.copy(
            habitLog = emptyList()
        ) }
        _logSuccess.value = false
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun scheduleReminder(context: Context, habitTitle: String, delayInMillis: Long) {
        val intent = Intent(context, HabitReminderReceiver::class.java).apply {
            putExtra("habit_title", habitTitle)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habitTitle.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + delayInMillis,
            pendingIntent
        )
    }


}