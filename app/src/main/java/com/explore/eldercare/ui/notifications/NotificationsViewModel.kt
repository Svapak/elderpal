package com.explore.eldercare.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.explore.eldercare.ui.notifications.data.Reminder
import com.explore.eldercare.ui.notifications.data.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    val adapter: RemindersAdapter
) : ViewModel() {

    lateinit var  getAllReminders : Flow<List<Reminder>>

    init {
        viewModelScope.launch {
            getAllReminders = reminderRepository.getAllReminder()
        }
    }

    fun addReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.addReminder(reminder)
        }
    }

    fun updateReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.updateAReminder(reminder)
        }
    }

    fun getReminderById(id: Long) = reminderRepository.getAReminderById(id)

    fun deleteReminder(reminder: Reminder){
        adapter.overdueListener {

        }
        viewModelScope.launch(Dispatchers.IO) {
            reminderRepository.deleteReminder(reminder)
        }
    }

}