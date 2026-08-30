package com.example.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.data.MotivationalQuote
import com.example.data.ReminderType
import com.example.data.SampleData
import java.util.Calendar

object NotificationHelper {
  const val CHANNEL_ID = "jee_study_reminders_channel"
  const val CHANNEL_NAME = "JEE Study & Avengers Motivation"
  const val CHANNEL_DESC = "Daily study reminders, inspirational battlecries, formula alerts, and streak shield notifications"
  const val NOTIFICATION_ID = 2026

  fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
        description = CHANNEL_DESC
        enableLights(true)
        lightColor = Color.parseColor("#4F46E5") // Brand Primary Indigo
        enableVibration(true)
        vibrationPattern = longArrayOf(0, 250, 100, 250)
      }
      val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
      notificationManager?.createNotificationChannel(channel)
    }
  }

  fun sendStudyReminder(
    context: Context,
    reminderType: ReminderType = ReminderType.AVENGERS_BATTLECRY,
    quote: MotivationalQuote? = null,
    isTest: Boolean = false
  ) {
    createNotificationChannel(context)

    val randomQuote = quote ?: SampleData.motivationalQuotes.random()

    // Intent to launch MainActivity when tapped
    val tapIntent = Intent(context, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
      putExtra("FROM_NOTIFICATION", true)
      putExtra("REMINDER_TYPE", reminderType.name)
    }

    val pendingIntent = PendingIntent.getActivity(
      context,
      0,
      tapIntent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val (title, contentText, bigText) = when (reminderType) {
      ReminderType.AVENGERS_BATTLECRY -> {
        val testPrefix = if (isTest) "[TEST NOTIFICATION] " else ""
        val t = "$testPrefix🛡️ ${randomQuote.authorOrHero}: Avengers Assemble!"
        val shortBody = "\"${randomQuote.quote.take(70)}...\""
        val fullBody = "“${randomQuote.quote}”\n\n— ${randomQuote.authorOrHero} (${randomQuote.category.label})\n\n💡 Defend your AIR: Complete today's planned topics and solve 25 PYQs now!"
        Triple(t, shortBody, fullBody)
      }
      ReminderType.FORMULA_BURST -> {
        val testPrefix = if (isTest) "[TEST NOTIFICATION] " else ""
        val t = "$testPrefix⚡️ Daily Formula Sprint • 5 Min Revision"
        val shortBody = "Review high-yield formulas in Modern Physics, Organic & Calculus!"
        val fullBody = "🎯 High-Yield Daily Sprint:\n• Photoelectric Equation: E = hν - Φ\n• De Broglie Wavelength: λ = h/p\n• Matrices: |adj(A)| = |A|^(n-1)\n\nTap to open Formula Vault and test yourself!"
        Triple(t, shortBody, fullBody)
      }
      ReminderType.STREAK_SHIELD -> {
        val testPrefix = if (isTest) "[TEST NOTIFICATION] " else ""
        val t = "$testPrefix🔥 Streak Shield: Defend Your Rank!"
        val shortBody = "Don't let your daily streak reset at midnight. Complete 1 study session."
        val fullBody = "🔥 You are on a multi-day study streak!\n\nConsistency is the #1 predictor of top 1,000 AIR in JEE. Complete a 30-minute revision or log today's hours now."
        Triple(t, shortBody, fullBody)
      }
      ReminderType.MOCK_TEST_DRILL -> {
        val testPrefix = if (isTest) "[TEST NOTIFICATION] " else ""
        val t = "$testPrefix📝 Mock Test Drill Alert"
        val shortBody = "Solve 30 timed PYQs under exam conditions today."
        val fullBody = "⏱️ Exam Simulation Time:\nSolve 30 PYQs with a 45-minute countdown. Focus on high accuracy and zero negative marks to jump +20 AIR points."
        Triple(t, shortBody, fullBody)
      }
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
      .setSmallIcon(android.R.drawable.ic_dialog_info)
      .setContentTitle(title)
      .setContentText(contentText)
      .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setContentIntent(pendingIntent)
      .setAutoCancel(true)
      .setColor(Color.parseColor("#4F46E5"))
      .addAction(
        android.R.drawable.ic_menu_agenda,
        "Open App & Study 🚀",
        pendingIntent
      )

    try {
      val notificationManager = NotificationManagerCompat.from(context)
      notificationManager.notify(NOTIFICATION_ID, builder.build())
    } catch (e: SecurityException) {
      // Permission not granted on Android 13+
    } catch (e: Exception) {
      // General safeguard
    }
  }

  fun scheduleDailyAlarm(
    context: Context,
    hour: Int,
    minute: Int,
    reminderType: ReminderType
  ) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

    val intent = Intent(context, StudyReminderReceiver::class.java).apply {
      action = "com.example.jee.ACTION_DAILY_STUDY_REMINDER"
      putExtra("REMINDER_TYPE", reminderType.name)
    }

    val pendingIntent = PendingIntent.getBroadcast(
      context,
      101,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
      set(Calendar.HOUR_OF_DAY, hour)
      set(Calendar.MINUTE, minute)
      set(Calendar.SECOND, 0)
      set(Calendar.MILLISECOND, 0)
      if (before(Calendar.getInstance())) {
        add(Calendar.DAY_OF_YEAR, 1)
      }
    }

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(
          AlarmManager.RTC_WAKEUP,
          calendar.timeInMillis,
          pendingIntent
        )
      } else {
        alarmManager.setRepeating(
          AlarmManager.RTC_WAKEUP,
          calendar.timeInMillis,
          AlarmManager.INTERVAL_DAY,
          pendingIntent
        )
      }
    } catch (e: SecurityException) {
      // Exact alarm permission not granted, fallback to inexact
      alarmManager.set(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
      )
    }
  }

  fun cancelDailyAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
    val intent = Intent(context, StudyReminderReceiver::class.java).apply {
      action = "com.example.jee.ACTION_DAILY_STUDY_REMINDER"
    }
    val pendingIntent = PendingIntent.getBroadcast(
      context,
      101,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
  }
}
