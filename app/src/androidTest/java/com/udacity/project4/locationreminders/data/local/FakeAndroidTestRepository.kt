package com.udacity.project4.locationreminders.data.local

import androidx.lifecycle.MutableLiveData
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import java.util.LinkedHashMap
import com.udacity.project4.locationreminders.data.dto.Result

class FakeAndroidTestRepository : ReminderDataSource {

    var remindersServiceData: LinkedHashMap<String, ReminderDTO> = LinkedHashMap()

    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }
    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        if(shouldReturnError){
            return Result.Error("Testing Result Error")
        }
        return Result.Success(remindersServiceData.values.toList())
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        remindersServiceData[reminder.id] = reminder
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
        if(shouldReturnError){
            return Result.Error("Testing Result Error")
        }
        remindersServiceData[id]?.let{
            return Result.Success(it)
        }
        return Result.Error("Could not find Reminder")
    }

    override suspend fun deleteAllReminders() {
        remindersServiceData.clear()
    }

    suspend fun addReminders(vararg reminders: ReminderDTO) {
        for (reminder in reminders) {
            reminders@this.saveReminder(reminder)
        }
    }

}