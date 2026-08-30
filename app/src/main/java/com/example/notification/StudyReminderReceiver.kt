package com.example.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.ReminderType

class StudyReminderReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent?) {
    val reminderTypeName = intent?.getStringExtra("REMINDER_TYPE")
    val reminderType = try {
      if (reminderTypeName != null) ReminderType.valueOf(reminderTypeName) else ReminderType.AVENGERS_BATTLECRY
    } catch (e: Exception) {
      ReminderType.AVENGERS_BATTLECRY
    }

    // Trigger local notification
    NotificationHelper.sendStudyReminder(
      context = context,
      reminderType = reminderType,
      isTest = false
    )

    // Re-schedule alarm for the next day
    // Default 7 PM if not configured
    NotificationHelper.scheduleDailyAlarm(
      context = context,
      hour = 19,
      minute = 0,
      reminderType = reminderType
    )
  }
}
