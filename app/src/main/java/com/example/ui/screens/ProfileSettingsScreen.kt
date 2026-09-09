package com.example.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import com.example.ui.components.UserAvatarView
import com.example.ui.components.EditProfileAvatarDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ReminderType
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel

@Composable
fun ProfileSettingsScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var editingName by remember { mutableStateOf(uiState.userProfile.name) }
  var editingDreamCollege by remember { mutableStateOf(uiState.userProfile.dreamCollege) }
  var editingDreamBranch by remember { mutableStateOf(uiState.userProfile.dreamBranch) }
  var showAvatarDialog by remember { mutableStateOf(false) }

  // Permission launcher for Android 13+ POST_NOTIFICATIONS
  val permissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      viewModel.setNotificationEnabled(context, true)
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("profile_settings_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Profile Hero Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("user_profile_hero"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Profile photo / preset avatar with edit capability
            UserAvatarView(
              userProfile = uiState.userProfile,
              size = 64.dp,
              isEditable = true,
              onClick = { showAvatarDialog = true },
              modifier = Modifier.testTag("profile_avatar_view")
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = uiState.userProfile.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Target JEE ${uiState.userProfile.targetYear} Aspirant",
                style = MaterialTheme.typography.bodySmall,
                color = BrandPrimary,
                fontWeight = FontWeight.SemiBold
              )
              Text(
                text = "Dream: ${uiState.userProfile.dreamCollege} • ${uiState.userProfile.dreamBranch}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Edit Avatar Quick Button
          OutlinedButton(
            onClick = { showAvatarDialog = true },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("open_avatar_picker_button"),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.6f))
          ) {
            Icon(
              imageVector = Icons.Default.CameraAlt,
              contentDescription = null,
              tint = BrandPrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Choose Avengers Avatar or Upload Photo 📸",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Mini statistics badges
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            ProfileStatPill(title = "Study Streak", value = "${uiState.userProfile.currentStreakDays} Days 🔥", color = BrandAccentGold)
            ProfileStatPill(title = "Target AIR", value = "< ${uiState.userProfile.targetAir}", color = BrandEmerald)
            ProfileStatPill(title = "Category", value = uiState.userProfile.category, color = BrandPrimary)
          }
        }
      }
    }

    // 1.5. EDIT PROFILE DETAILS CARD
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("edit_profile_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = BrandPrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Personal Aspirant Details",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          OutlinedTextField(
            value = editingName,
            onValueChange = { editingName = it },
            label = { Text("Your Name (Shown on Home)") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("profile_name_input")
          )

          OutlinedTextField(
            value = editingDreamCollege,
            onValueChange = { editingDreamCollege = it },
            label = { Text("Dream College (e.g. IIT Bombay)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = editingDreamBranch,
            onValueChange = { editingDreamBranch = it },
            label = { Text("Dream Branch (e.g. CSE / Electrical)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Button(
            onClick = {
              if (editingName.isNotBlank()) {
                viewModel.updateUserProfile(
                  name = editingName.trim(),
                  dreamCollege = editingDreamCollege.trim(),
                  dreamBranch = editingDreamBranch.trim()
                )
                android.widget.Toast.makeText(context, "Profile updated successfully! ✨", android.widget.Toast.LENGTH_SHORT).show()
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("save_profile_button")
          ) {
            Text(
              text = "Save Profile Changes",
              color = Color.Black,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    // 2. THEME MODE SELECTOR (OLED PURE BLACK / DARK SLATE / LIGHT / SYSTEM)
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("theme_selector_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Palette,
              contentDescription = "Theme",
              tint = BrandPrimary,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "Display Theme & Dark Mode",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "OLED Pure Black (#000000) for late-night study & AMOLED battery saving",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 4 Theme Options
          ThemeOptionRow(
            title = "Pure Black (OLED AMOLED)",
            subtitle = "True #000000 pitch black canvas for minimal eye strain",
            isSelected = uiState.themeMode == AppThemeMode.OLED_BLACK,
            icon = "⬛",
            onClick = { viewModel.setThemeMode(AppThemeMode.OLED_BLACK) }
          )

          Spacer(modifier = Modifier.height(10.dp))

          ThemeOptionRow(
            title = "Dark Slate",
            subtitle = "Modern deep navy dark theme",
            isSelected = uiState.themeMode == AppThemeMode.DARK,
            icon = "🌙",
            onClick = { viewModel.setThemeMode(AppThemeMode.DARK) }
          )

          Spacer(modifier = Modifier.height(10.dp))

          ThemeOptionRow(
            title = "Light Theme",
            subtitle = "High-contrast daytime reading layout",
            isSelected = uiState.themeMode == AppThemeMode.LIGHT,
            icon = "☀️",
            onClick = { viewModel.setThemeMode(AppThemeMode.LIGHT) }
          )

          Spacer(modifier = Modifier.height(10.dp))

          ThemeOptionRow(
            title = "System Default",
            subtitle = "Automatically match Android system dark/light mode",
            isSelected = uiState.themeMode == AppThemeMode.SYSTEM,
            icon = "⚙️",
            onClick = { viewModel.setThemeMode(AppThemeMode.SYSTEM) }
          )
        }
      }
    }

    // 3. Exam Target Customization
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Target Score & Goals",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(12.dp))

          // Target Year Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Target JEE Year",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface
            )
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(2.dp)
            ) {
              listOf(2026, 2027).forEach { year ->
                val isSelected = uiState.userProfile.targetYear == year
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) BrandPrimary else Color.Transparent)
                    .clickable { viewModel.updateTargetYear(year) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = "$year",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Target Score Slider
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Target JEE Main Score",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "${uiState.userProfile.targetScoreMains} / 300",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.ExtraBold,
              fontFamily = FontFamily.Monospace,
              color = BrandPrimary
            )
          }

          Slider(
            value = uiState.userProfile.targetScoreMains.toFloat(),
            onValueChange = { viewModel.updateTargetScore(it.toInt()) },
            valueRange = 100f..300f,
            colors = SliderDefaults.colors(
              thumbColor = BrandPrimary,
              activeTrackColor = BrandPrimary,
              inactiveTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
          )
        }
      }
    }

    // 4. DAILY STUDY REMINDERS & AVENGERS NOTIFICATION PROTOCOL
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("notification_settings_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, if (uiState.notificationSettings.enabled) BrandPrimary.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          // Card Header with Toggle
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(if (uiState.notificationSettings.enabled) BrandPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (uiState.notificationSettings.enabled) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                  contentDescription = "Notification",
                  tint = if (uiState.notificationSettings.enabled) BrandPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Daily Study & Avengers Alert",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = if (uiState.notificationSettings.enabled) "Scheduled daily at ${uiState.notificationSettings.formattedTime}" else "Notifications disabled",
                  style = MaterialTheme.typography.bodySmall,
                  color = if (uiState.notificationSettings.enabled) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }

            Switch(
              checked = uiState.notificationSettings.enabled,
              onCheckedChange = { isChecked ->
                if (isChecked) {
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                  } else {
                    viewModel.setNotificationEnabled(context, true)
                  }
                } else {
                  viewModel.setNotificationEnabled(context, false)
                }
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                checkedTrackColor = BrandPrimary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
              ),
              modifier = Modifier.testTag("notification_switch")
            )
          }

          if (uiState.notificationSettings.enabled) {
            // Preset Time Slots
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = "Reminder Schedule Time",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )

              val timeSlots = listOf(
                Pair(7, 0) to "🌅 07:00 AM (Morning)",
                Pair(14, 0) to "☀️ 02:00 PM (Afternoon)",
                Pair(19, 0) to "🎯 07:00 PM (Prime)",
                Pair(22, 0) to "🌙 10:00 PM (Night)"
              )

              LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(timeSlots) { (time, label) ->
                  val isSelected = uiState.notificationSettings.reminderHour == time.first && uiState.notificationSettings.reminderMinute == time.second
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(10.dp))
                      .background(if (isSelected) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant)
                      .border(
                        1.dp,
                        if (isSelected) BrandPrimary else Color.Transparent,
                        RoundedCornerShape(10.dp)
                      )
                      .clickable { viewModel.setReminderTime(context, time.first, time.second) }
                      .padding(horizontal = 10.dp, vertical = 6.dp)
                  ) {
                    Text(
                      text = label,
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                    )
                  }
                }
              }
            }

            // Reminder Theme & Style Selectors
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = "Motivation Mode & Content",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )

              Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ReminderType.entries.forEach { rType ->
                  val isSelected = uiState.notificationSettings.reminderType == rType
                  OutlinedCard(
                    modifier = Modifier
                      .fillMaxWidth()
                      .clickable { viewModel.setReminderType(context, rType) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(
                      containerColor = if (isSelected) BrandPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    ),
                    border = BorderStroke(
                      if (isSelected) 1.5.dp else 1.dp,
                      if (isSelected) BrandPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                  ) {
                    Row(
                      modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                      Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                      ) {
                        Text(text = rType.iconEmoji, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                          Text(
                            text = rType.title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                          )
                          Text(
                            text = rType.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                          )
                        }
                      }

                      if (isSelected) {
                        Box(
                          modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(BrandPrimary),
                          contentAlignment = Alignment.Center
                        ) {
                          Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                        }
                      }
                    }
                  }
                }
              }
            }

            // Live Preview of Local Notification
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(12.dp)
            ) {
              Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🔔", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "NOTIFICATION PREVIEW",
                      style = MaterialTheme.typography.labelSmall,
                      fontSize = 9.sp,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                  Text(
                    text = "now",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }

                Text(
                  text = when (uiState.notificationSettings.reminderType) {
                    ReminderType.AVENGERS_BATTLECRY -> "🛡️ Iron Man: Avengers Assemble!"
                    ReminderType.FORMULA_BURST -> "⚡️ Daily Formula Sprint • 5 Min Revision"
                    ReminderType.STREAK_SHIELD -> "🔥 Streak Shield: Defend Your Rank!"
                    ReminderType.MOCK_TEST_DRILL -> "📝 Mock Test Drill Alert"
                  },
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                  color = BrandPrimary
                )

                Text(
                  text = when (uiState.notificationSettings.reminderType) {
                    ReminderType.AVENGERS_BATTLECRY -> "“Part of the journey is the end. Give every formula your 100%.” — Defend your AIR: Complete today's planned topics!"
                    ReminderType.FORMULA_BURST -> "Review high-yield formulas in Modern Physics, Organic & Calculus before starting today's session."
                    ReminderType.STREAK_SHIELD -> "Don't let your ${uiState.userProfile.currentStreakDays}-day streak reset tonight. Log 30 minutes of study."
                    ReminderType.MOCK_TEST_DRILL -> "Solve 30 timed PYQs under exam conditions. Focus on high accuracy and zero negative marks."
                  },
                  style = MaterialTheme.typography.bodySmall,
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurface,
                  lineHeight = 15.sp
                )
              }
            }

            // Test Send Button & Feedback status
            Button(
              onClick = { viewModel.sendTestNotificationNow(context) },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("send_test_notification_btn"),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = BrandPrimary,
                contentColor = Color.Black
              )
            ) {
              Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Send Instant Test Notification 🚀",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
            }

            if (uiState.lastNotificationSentMessage != null) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(BrandEmerald.copy(alpha = 0.15f))
                  .padding(8.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Text(
                    text = "✅ ${uiState.lastNotificationSentMessage}",
                    style = MaterialTheme.typography.labelSmall,
                    color = BrandEmerald,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                  )
                  Text(
                    text = "Dismiss",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = BrandEmerald,
                    modifier = Modifier.clickable { viewModel.clearNotificationStatusMessage() }
                  )
                }
              }
            }
          }
        }
      }
    }

    // 5. About App & JEE Prep Guarantee
    item {
      OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = BrandEmerald,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "JEE Prep 2026/2027 Pro",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Offline-First • Syllabus Tracker • Rank Jump Simulator • Formula Vault • IITian Guidance",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }

  if (showAvatarDialog) {
    EditProfileAvatarDialog(
      userProfile = uiState.userProfile,
      onSaveAvatar = { customUri, presetId, colorHex, clearCustom ->
        viewModel.updateUserProfile(
          avatarUri = customUri,
          avatarPreset = presetId,
          avatarColorHex = colorHex,
          clearCustomImage = clearCustom
        )
      },
      onDismiss = { showAvatarDialog = false }
    )
  }
}

@Composable
fun ProfileStatPill(
  title: String,
  value: String,
  color: Color
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(12.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .padding(horizontal = 10.dp, vertical = 8.dp)
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 10.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.ExtraBold,
        color = color
      )
    }
  }
}

@Composable
fun ThemeOptionRow(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  icon: String,
  onClick: () -> Unit
) {
  OutlinedCard(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("theme_option_${title.take(4)}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.outlinedCardColors(
      containerColor = if (isSelected) BrandPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ),
    border = BorderStroke(
      1.dp,
      if (isSelected) BrandPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Text(text = icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        }
      }

      if (isSelected) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(BrandPrimary),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Selected",
            tint = Color.Black,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
