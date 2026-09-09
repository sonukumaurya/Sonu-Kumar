package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DailyGoal
import com.example.data.SubjectType
import com.example.ui.components.AvengersMotivationCard
import com.example.ui.components.AvengersQuotesDeckDialog
import com.example.ui.components.CountdownTimerHero
import com.example.ui.components.DailyGoalItemCard
import com.example.ui.components.EditProfileAvatarDialog
import com.example.ui.components.StreakTargetWidget
import com.example.ui.components.SubjectMasteryProgressOverview
import com.example.ui.components.UserAvatarView
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel

@Composable
fun DashboardScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  onNavigateToTab: (Int) -> Unit,
  onNavigateToSyllabusWithSubject: (SubjectType) -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddGoalDialog by remember { mutableStateOf(false) }
  var showQuotesDeckDialog by remember { mutableStateOf(false) }
  var showEditNameDialog by remember { mutableStateOf(false) }
  var showAvatarDialog by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("dashboard_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 0. Primary Personalized User Greeting Header
    item {
      UserGreetingHeaderCard(
        userProfile = uiState.userProfile,
        onEditName = { showEditNameDialog = true },
        onEditAvatar = { showAvatarDialog = true },
        onOpenSettings = { onNavigateToTab(6) }
      )
    }

    // 1. Daily Avengers Motivation & Hero Quote Card
    item {
      AvengersMotivationCard(
        quote = uiState.activeQuote,
        arcReactorEnergy = uiState.userProfile.arcReactorEnergy,
        currentStreakDays = uiState.userProfile.currentStreakDays,
        onNextQuote = { viewModel.nextMotivationalQuote() },
        onPrevQuote = { viewModel.prevMotivationalQuote() },
        onRandomQuote = { viewModel.randomMotivationalQuote() },
        onToggleBookmark = { viewModel.toggleQuoteBookmark(uiState.activeQuote.id) },
        onBoostEnergy = { viewModel.boostArcReactorEnergy() },
        onOpenDeck = { showQuotesDeckDialog = true }
      )
    }

    // 2. Countdown Hero Timer Card
    item {
      CountdownTimerHero(targetYear = uiState.userProfile.targetYear)
    }

    // 3. Daily Streak & Target Score Widget
    item {
      StreakTargetWidget(
        streakDays = uiState.userProfile.currentStreakDays,
        targetScore = uiState.userProfile.targetScoreMains,
        totalScore = uiState.totalScore,
        predictedPercentile = uiState.predictedPercentile
      )
    }

    // 4. Visual Progress & Recharts-style Analytics Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onNavigateToTab(7) }
          .testTag("dashboard_visual_analytics_banner"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandPrimary.copy(alpha = 0.6f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                  Brush.linearGradient(
                    listOf(BrandPrimary, BrandSecondary)
                  )
                ),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = "Visual Charts",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Visual Progress Dashboard",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(BrandEmerald.copy(alpha = 0.2f))
                    .padding(horizontal = 5.dp, vertical = 1.dp)
                ) {
                  Text("LIVE", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = BrandEmerald)
                }
              }
              Text(
                text = "Plot syllabus completion & practice test scores over time",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Button(
            onClick = { onNavigateToTab(7) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary, contentColor = Color.Black),
            modifier = Modifier.padding(start = 8.dp)
          ) {
            Text("Open 📊", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // 5. Quick Action Shortcut Pills
    item {
      QuickToolsGrid(
        onNavigateToTab = onNavigateToTab
      )
    }

    // 4. Subject Syllabus Mastery Card
    item {
      SubjectMasteryProgressOverview(
        physicsProgress = uiState.physicsProgress,
        chemistryProgress = uiState.chemistryProgress,
        mathProgress = uiState.mathProgress,
        onNavigateToSyllabus = { subject ->
          viewModel.setSelectedSubjectTab(subject)
          onNavigateToSyllabusWithSubject(subject)
        }
      )
    }

    // 5. Daily Goals / Action Plan Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Today's Study Targets",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.width(8.dp))
          val completedCount = uiState.dailyGoals.count { it.isCompleted }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(BrandEmerald.copy(alpha = 0.2f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "$completedCount/${uiState.dailyGoals.size}",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = BrandEmerald
            )
          }
        }

        IconButton(
          onClick = { showAddGoalDialog = true },
          modifier = Modifier.testTag("add_goal_button")
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Goal",
            tint = BrandPrimary
          )
        }
      }
    }

    items(uiState.dailyGoals) { goal ->
      DailyGoalItemCard(
        goal = goal,
        onToggle = { viewModel.toggleDailyGoal(goal.id) },
        onDelete = { viewModel.deleteDailyGoal(goal.id) }
      )
    }

    // 6. Flash Formula of the Day Card
    item {
      FlashFormulaCard(
        formula = uiState.formulas.firstOrNull() ?: return@item,
        onViewAllFormulas = { onNavigateToTab(4) } // Tab 4 is Formulas
      )
    }

    // 7. Upcoming Mock Test Card
    item {
      val mockTest = uiState.mockTests.firstOrNull()
      if (mockTest != null) {
        UpcomingMockCard(mockTest = mockTest)
      }
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }

  if (showAddGoalDialog) {
    AddGoalDialog(
      onDismiss = { showAddGoalDialog = false },
      onAdd = { title, subject, duration, isHighPriority ->
        viewModel.addDailyGoal(title, subject, duration, isHighPriority)
        showAddGoalDialog = false
      }
    )
  }

  if (showQuotesDeckDialog) {
    AvengersQuotesDeckDialog(
      quotes = uiState.motivationalQuotes,
      selectedCategory = uiState.selectedQuoteCategory,
      onSelectCategory = { viewModel.setSelectedQuoteCategory(it) },
      onToggleBookmark = { viewModel.toggleQuoteBookmark(it) },
      onDismiss = { showQuotesDeckDialog = false }
    )
  }

  if (showEditNameDialog) {
    QuickEditNameDialog(
      currentName = uiState.userProfile.name,
      currentCollege = uiState.userProfile.dreamCollege,
      onSave = { newName, newCollege ->
        viewModel.updateUserProfile(name = newName, dreamCollege = newCollege)
        showEditNameDialog = false
      },
      onDismiss = { showEditNameDialog = false }
    )
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
fun QuickToolsGrid(
  onNavigateToTab: (Int) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = "Quick Tools & Simulators",
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(10.dp))
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      QuickToolButton(
        title = "Analytics",
        subtitle = "Visual Graphs",
        icon = Icons.Default.TrendingUp,
        accentColor = BrandEmerald,
        onClick = { onNavigateToTab(7) }, // Tab 7 is Analytics & Visual Progress
        modifier = Modifier.weight(1f)
      )
      QuickToolButton(
        title = "AIR Predictor",
        subtitle = "Score & Rank",
        icon = Icons.Default.Calculate,
        accentColor = BrandPrimary,
        onClick = { onNavigateToTab(3) }, // Tab 3 is Rank & Simulator
        modifier = Modifier.weight(1f)
      )
      QuickToolButton(
        title = "Formula Vault",
        subtitle = "150+ Sheets",
        icon = Icons.Default.MenuBook,
        accentColor = BrandAccentGold,
        onClick = { onNavigateToTab(4) },
        modifier = Modifier.weight(1f)
      )
      QuickToolButton(
        title = "Ask Mentor",
        subtitle = "1-on-1 Chat",
        icon = Icons.Default.ChatBubbleOutline,
        accentColor = BrandSecondary,
        onClick = { onNavigateToTab(5) }, // Tab 5 is Colleges & Mentors
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
fun QuickToolButton(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedCard(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier.padding(10.dp),
      horizontalAlignment = Alignment.Start
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(accentColor.copy(alpha = 0.18f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 10.sp,
        maxLines = 1
      )
    }
  }
}

@Composable
fun FlashFormulaCard(
  formula: com.example.data.FormulaCard,
  onViewAllFormulas: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("flash_formula_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = BrandAccentGold,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Formula of the Day",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = BrandAccentGold
          )
        }
        Text(
          text = "All Formulas →",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = BrandPrimary,
          modifier = Modifier.clickable(onClick = onViewAllFormulas)
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "${formula.title} (${formula.topic})",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(6.dp))

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Text(
          text = formula.formula,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.ExtraBold,
          fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
          color = formula.subject.color
        )
      }
    }
  }
}

@Composable
fun UpcomingMockCard(
  mockTest: com.example.data.MockTest,
  modifier: Modifier = Modifier
) {
  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("upcoming_mock_card"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = androidx.compose.foundation.BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.4f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(BrandPrimary.copy(alpha = 0.18f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "📅 ${mockTest.dateFormatted}",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = mockTest.title,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Duration: ${mockTest.durationHours} hrs • Max Marks: ${mockTest.totalMarks}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .background(BrandPrimary)
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Text(
          text = "Registered ✓",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = Color.Black
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalDialog(
  onDismiss: () -> Unit,
  onAdd: (title: String, subject: SubjectType, duration: Int, isHighPriority: Boolean) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var selectedSubject by remember { mutableStateOf(SubjectType.PHYSICS) }
  var durationText by remember { mutableStateOf("30") }
  var isHighPriority by remember { mutableStateOf(false) }
  var expandedSubjectDropdown by remember { mutableStateOf(false) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Add Daily Study Target",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("Task Description") },
          placeholder = { Text("e.g. Solve 20 PYQs of Electrostatics") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("goal_title_input"),
          singleLine = true
        )

        ExposedDropdownMenuBox(
          expanded = expandedSubjectDropdown,
          onExpandedChange = { expandedSubjectDropdown = it }
        ) {
          OutlinedTextField(
            value = selectedSubject.displayName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Subject") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSubjectDropdown) },
            modifier = Modifier
              .fillMaxWidth()
              .menuAnchor(MenuAnchorType.PrimaryNotEditable)
          )
          ExposedDropdownMenu(
            expanded = expandedSubjectDropdown,
            onDismissRequest = { expandedSubjectDropdown = false }
          ) {
            SubjectType.entries.forEach { subject ->
              DropdownMenuItem(
                text = { Text(subject.displayName, color = subject.color, fontWeight = FontWeight.Bold) },
                onClick = {
                  selectedSubject = subject
                  expandedSubjectDropdown = false
                }
              )
            }
          }
        }

        OutlinedTextField(
          value = durationText,
          onValueChange = { durationText = it.filter { char -> char.isDigit() } },
          label = { Text("Estimated Duration (Minutes)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Mark as High Priority 🔥",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
          )
          Switch(
            checked = isHighPriority,
            onCheckedChange = { isHighPriority = it }
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val dur = durationText.toIntOrNull() ?: 30
          if (title.isNotBlank()) {
            onAdd(title.trim(), selectedSubject, dur, isHighPriority)
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
        modifier = Modifier.testTag("confirm_add_goal_button")
      ) {
        Text("Add Target", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

@Composable
fun UserGreetingHeaderCard(
  userProfile: com.example.data.UserProfile,
  onEditName: () -> Unit,
  onEditAvatar: () -> Unit,
  onOpenSettings: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("user_greeting_header_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = androidx.compose.foundation.BorderStroke(1.5.dp, BrandPrimary.copy(alpha = 0.5f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Top Greeting Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // User Avatar with Custom Photo / Preset / Monogram + Editable Badge
          UserAvatarView(
            userProfile = userProfile,
            size = 50.dp,
            isEditable = true,
            onClick = onEditAvatar,
            modifier = Modifier.testTag("home_user_avatar")
          )

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.clickable { onEditName() }
            ) {
              Text(
                text = "Hi, ${userProfile.name} 👋",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.testTag("home_user_greeting_title")
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Name",
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Target JEE ${userProfile.targetYear} • AIR < ${userProfile.targetAir}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = BrandPrimary
              )
            }
          }
        }

        // Settings / Profile button
        IconButton(
          onClick = onOpenSettings,
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .testTag("home_profile_settings_button")
        ) {
          Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Quick Badges Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        GreetingPill(
          icon = "🔥",
          label = "${userProfile.currentStreakDays} Days Streak",
          backgroundColor = BrandAccentGold.copy(alpha = 0.15f),
          textColor = BrandAccentGold
        )
        GreetingPill(
          icon = "🏛️",
          label = userProfile.dreamCollege,
          backgroundColor = BrandEmerald.copy(alpha = 0.15f),
          textColor = BrandEmerald
        )
        GreetingPill(
          icon = "⚡️",
          label = "${userProfile.arcReactorEnergy}% Energy",
          backgroundColor = BrandPrimary.copy(alpha = 0.15f),
          textColor = BrandPrimary
        )
      }
    }
  }
}

@Composable
fun GreetingPill(
  icon: String,
  label: String,
  backgroundColor: Color,
  textColor: Color
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(backgroundColor)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(text = icon, fontSize = 11.sp)
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
      )
    }
  }
}

@Composable
fun QuickEditNameDialog(
  currentName: String,
  currentCollege: String,
  onSave: (String, String) -> Unit,
  onDismiss: () -> Unit
) {
  var nameText by remember { mutableStateOf(currentName) }
  var collegeText by remember { mutableStateOf(currentCollege) }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Person,
          contentDescription = null,
          tint = BrandPrimary,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Edit Your Name & Profile", fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedTextField(
          value = nameText,
          onValueChange = { nameText = it },
          label = { Text("Your Name") },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("edit_name_text_field")
        )

        OutlinedTextField(
          value = collegeText,
          onValueChange = { collegeText = it },
          label = { Text("Dream College (e.g., IIT Bombay)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          if (nameText.isNotBlank()) {
            onSave(nameText.trim(), collegeText.trim())
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
        modifier = Modifier.testTag("save_name_dialog_button")
      ) {
        Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

