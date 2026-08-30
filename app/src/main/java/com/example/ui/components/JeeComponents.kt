package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.data.MotivationalQuote
import com.example.data.QuoteCategory
import com.example.data.SyllabusTopic
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Chapter
import com.example.data.ChapterStatus
import com.example.data.College
import com.example.data.DailyGoal
import com.example.data.FormulaCard
import com.example.data.Mentor
import com.example.data.MockTest
import com.example.data.SubjectType
import com.example.data.WeightageLevel
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor

@Composable
fun CountdownTimerHero(
  targetYear: Int,
  modifier: Modifier = Modifier
) {
  // Configured exam target dates
  // Assuming target is Jan 24, 2027, Apr 06, 2027, May 23, 2027 or appropriate year
  val actualYear = if (targetYear < 2026) 2026 else targetYear
  
  // Timestamps (calculated using standard calendar timestamps)
  val examTargets = remember(actualYear) {
    // Jan 24, 2027 09:00:00 AM IST (UTC+5:30)
    val s1Millis = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.YEAR, actualYear + 1)
      set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY)
      set(java.util.Calendar.DAY_OF_MONTH, 24)
      set(java.util.Calendar.HOUR_OF_DAY, 9)
      set(java.util.Calendar.MINUTE, 0)
      set(java.util.Calendar.SECOND, 0)
      set(java.util.Calendar.MILLISECOND, 0)
    }.timeInMillis

    // Apr 06, 2027 09:00:00 AM IST
    val s2Millis = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.YEAR, actualYear + 1)
      set(java.util.Calendar.MONTH, java.util.Calendar.APRIL)
      set(java.util.Calendar.DAY_OF_MONTH, 6)
      set(java.util.Calendar.HOUR_OF_DAY, 9)
      set(java.util.Calendar.MINUTE, 0)
      set(java.util.Calendar.SECOND, 0)
      set(java.util.Calendar.MILLISECOND, 0)
    }.timeInMillis

    // May 23, 2027 09:00:00 AM IST
    val advMillis = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.YEAR, actualYear + 1)
      set(java.util.Calendar.MONTH, java.util.Calendar.MAY)
      set(java.util.Calendar.DAY_OF_MONTH, 23)
      set(java.util.Calendar.HOUR_OF_DAY, 9)
      set(java.util.Calendar.MINUTE, 0)
      set(java.util.Calendar.SECOND, 0)
      set(java.util.Calendar.MILLISECOND, 0)
    }.timeInMillis

    listOf(
      ExamCountdownInfo(
        id = "main_s1",
        label = "JEE Main S1",
        fullTitle = "JEE Main ${actualYear + 1} • Session 1",
        dateString = "Jan 24, ${actualYear + 1} (Morning Shift)",
        targetMillis = s1Millis,
        accentColor = BrandPrimary
      ),
      ExamCountdownInfo(
        id = "main_s2",
        label = "JEE Main S2",
        fullTitle = "JEE Main ${actualYear + 1} • Session 2",
        dateString = "Apr 06, ${actualYear + 1} (Morning Shift)",
        targetMillis = s2Millis,
        accentColor = BrandAccentGold
      ),
      ExamCountdownInfo(
        id = "adv",
        label = "JEE Advanced",
        fullTitle = "JEE Advanced ${actualYear + 1} • IIT Admission",
        dateString = "May 23, ${actualYear + 1} (Paper 1 & 2)",
        targetMillis = advMillis,
        accentColor = BrandEmerald
      )
    )
  }

  var selectedExamIndex by remember { mutableStateOf(0) }
  val activeExam = examTargets[selectedExamIndex.coerceIn(0, examTargets.lastIndex)]

  // Real-time ticking state updated every 1000ms
  var currentTimeMillis by remember { mutableStateOf(System.currentTimeMillis()) }

  androidx.compose.runtime.LaunchedEffect(Unit) {
    while (true) {
      currentTimeMillis = System.currentTimeMillis()
      kotlinx.coroutines.delay(1000L)
    }
  }

  val diffMillis = (activeExam.targetMillis - currentTimeMillis).coerceAtLeast(0L)
  val days = diffMillis / (1000 * 60 * 60 * 24)
  val hours = (diffMillis / (1000 * 60 * 60)) % 24
  val minutes = (diffMillis / (1000 * 60)) % 60
  val seconds = (diffMillis / 1000) % 60
  val totalStudyHoursAvailable = days * 8 // Assuming 8 productive hours per day

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("countdown_hero_card"),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(1.5.dp, activeExam.accentColor.copy(alpha = 0.6f)),
    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      // Header & Exam Selector Tabs
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Blinking live pulse indicator
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(BrandRose)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "LIVE COUNTDOWN",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = BrandRose,
            letterSpacing = 1.sp
          )
        }

        // Toggle Exam Pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(2.dp)
        ) {
          examTargets.forEachIndexed { index, exam ->
            val isSelected = selectedExamIndex == index
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) activeExam.accentColor else Color.Transparent)
                .clickable { selectedExamIndex = index }
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = exam.label,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Exam Title & Exact Target Date
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = activeExam.fullTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "📅 ${activeExam.dateString}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = activeExam.accentColor
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "09:00 AM IST",
            style = MaterialTheme.typography.labelSmall,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Countdown Big Digits: DAYS : HOURS : MINS : SECS
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        CountdownDigitBox(
          value = days.toString(),
          label = "DAYS",
          highlightColor = BrandPrimary,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = ":",
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 2.dp)
        )
        CountdownDigitBox(
          value = hours.toString().padStart(2, '0'),
          label = "HOURS",
          highlightColor = BrandAccentGold,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = ":",
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 2.dp)
        )
        CountdownDigitBox(
          value = minutes.toString().padStart(2, '0'),
          label = "MINS",
          highlightColor = BrandEmerald,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = ":",
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 2.dp)
        )
        CountdownDigitBox(
          value = seconds.toString().padStart(2, '0'),
          label = "SECS",
          highlightColor = BrandRose,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Study Budget & Pace Metric Pill
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(
            Brush.horizontalGradient(
              listOf(
                activeExam.accentColor.copy(alpha = 0.12f),
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
              )
            )
          )
          .border(1.dp, activeExam.accentColor.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
          .padding(horizontal = 12.dp, vertical = 8.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.ElectricBolt,
              contentDescription = null,
              tint = BrandAccentGold,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Time Budget: ~$totalStudyHoursAvailable hrs (@ 8h/day)",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Text(
            text = "${days / 7} Weeks Left",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = activeExam.accentColor
          )
        }
      }
    }
  }
}

data class ExamCountdownInfo(
  val id: String,
  val label: String,
  val fullTitle: String,
  val dateString: String,
  val targetMillis: Long,
  val accentColor: Color
)

@Composable
fun CountdownDigitBox(
  value: String,
  label: String,
  highlightColor: Color,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .border(1.dp, highlightColor.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
        .padding(vertical = 10.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = value,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
        color = highlightColor
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = FontWeight.Bold,
      fontSize = 9.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      letterSpacing = 0.5.sp
    )
  }
}

@Composable
fun StreakTargetWidget(
  streakDays: Int,
  targetScore: Int,
  totalScore: Int,
  predictedPercentile: Double,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    // Daily Streak Card
    Card(
      modifier = Modifier
        .weight(1f)
        .testTag("streak_card"),
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(BrandAccentGold.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = "Streak",
            tint = BrandAccentGold,
            modifier = Modifier.size(24.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "$streakDays Days",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Study Streak 🔥",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // Target Score & Percentile Card
    Card(
      modifier = Modifier
        .weight(1.1f)
        .testTag("target_score_card"),
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ) {
      Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(BrandPrimary.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.School,
            contentDescription = "Target",
            tint = BrandPrimary,
            modifier = Modifier.size(22.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "$totalScore / $targetScore",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
          Text(
            text = "$predictedPercentile %ile Est.",
            style = MaterialTheme.typography.labelSmall,
            color = BrandEmerald,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  }
}

@Composable
fun SubjectMasteryProgressOverview(
  physicsProgress: Float,
  chemistryProgress: Float,
  mathProgress: Float,
  onNavigateToSyllabus: (SubjectType) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("subject_overview_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Syllabus Mastery Tracker",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "View Chapters →",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.SemiBold,
          color = BrandPrimary,
          modifier = Modifier.clickable { onNavigateToSyllabus(SubjectType.PHYSICS) }
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        SubjectProgressMiniCard(
          subject = SubjectType.PHYSICS,
          progress = physicsProgress,
          onClick = { onNavigateToSyllabus(SubjectType.PHYSICS) },
          modifier = Modifier.weight(1f)
        )
        SubjectProgressMiniCard(
          subject = SubjectType.CHEMISTRY,
          progress = chemistryProgress,
          onClick = { onNavigateToSyllabus(SubjectType.CHEMISTRY) },
          modifier = Modifier.weight(1f)
        )
        SubjectProgressMiniCard(
          subject = SubjectType.MATHEMATICS,
          progress = mathProgress,
          onClick = { onNavigateToSyllabus(SubjectType.MATHEMATICS) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
fun SubjectProgressMiniCard(
  subject: SubjectType,
  progress: Float,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedProgress by animateFloatAsState(
    targetValue = progress,
    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
    label = "subject_progress"
  )
  val percentInt = (progress * 100).toInt()

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(14.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .clickable(onClick = onClick)
      .padding(10.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = subject.shortCode,
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.ExtraBold,
          color = subject.color
        )
        Text(
          text = "$percentInt%",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = subject.displayName,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(8.dp))

      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = subject.color,
        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
      )
    }
  }
}

@Composable
fun DailyGoalItemCard(
  goal: DailyGoal,
  onToggle: () -> Unit,
  onDelete: () -> Unit,
  modifier: Modifier = Modifier
) {
  val animatedCheckColor by animateColorAsState(
    targetValue = if (goal.isCompleted) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
    label = "goal_check_color"
  )

  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onToggle)
      .testTag("goal_item_${goal.id}"),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.outlinedCardColors(
      containerColor = if (goal.isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(
      1.dp,
      if (goal.isHighPriority && !goal.isCompleted) BrandAccentGold.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        IconButton(
          onClick = onToggle,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = if (goal.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = if (goal.isCompleted) "Completed" else "Pending",
            tint = animatedCheckColor,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Column {
          Text(
            text = goal.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (goal.isCompleted) FontWeight.Normal else FontWeight.Medium,
            color = if (goal.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
            textDecoration = if (goal.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )

          Spacer(modifier = Modifier.height(2.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(goal.subject.color.copy(alpha = 0.18f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = goal.subject.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = goal.subject.color,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Text(
              text = "⏱️ ${goal.durationMinutes}m",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (goal.isHighPriority) {
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "🔥 High Priority",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = BrandAccentGold
              )
            }
          }
        }
      }

      IconButton(
        onClick = onDelete,
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = Icons.Default.DeleteOutline,
          contentDescription = "Delete",
          tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChapterCardItem(
  chapter: Chapter,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit,
  onCycleStatus: () -> Unit,
  onIncrementPyqs: () -> Unit,
  onViewFormulas: () -> Unit,
  onToggleTopic: (String) -> Unit,
  onMarkAllTopics: (Boolean) -> Unit,
  onAddTopicClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val statusColor = when (chapter.status) {
    ChapterStatus.NOT_STARTED -> MaterialTheme.colorScheme.onSurfaceVariant
    ChapterStatus.IN_PROGRESS -> BrandAccentGold
    ChapterStatus.REVISED -> BrandSecondary
    ChapterStatus.MASTERED -> BrandEmerald
  }

  val pyqFraction = (chapter.pyqsSolved.toFloat() / chapter.totalPyqs.toFloat()).coerceIn(0f, 1f)
  val topicFraction = chapter.topicCompletionFraction
  val completedTopics = chapter.completedTopicCount
  val totalTopics = chapter.totalTopicCount

  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("chapter_card_${chapter.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, if (isExpanded) chapter.subject.color.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Header: Class & Weightage Badge + Status Selector
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(chapter.subject.color.copy(alpha = 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "Class ${chapter.classLevel}",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = chapter.subject.color
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(chapter.weightage.badgeColor.copy(alpha = 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = chapter.weightage.label,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = chapter.weightage.badgeColor
            )
          }
        }

        // Status pill (Clickable to cycle!)
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(statusColor.copy(alpha = 0.18f))
            .clickable(onClick = onCycleStatus)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "● ${chapter.status.label}",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = statusColor
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Chapter Title & Expand Header
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onToggleExpand),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = chapter.name,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.weight(1f)
        )

        IconButton(
          onClick = onToggleExpand,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Collapse Topics" else "Expand Topics",
            tint = chapter.subject.color
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Topic Checklist Progress Indicator
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
          .clickable(onClick = onToggleExpand)
          .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.FormatListBulleted,
            contentDescription = null,
            tint = chapter.subject.color,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Checklist: $completedTopics/$totalTopics Topics",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "${(topicFraction * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = if (topicFraction == 1f) BrandEmerald else chapter.subject.color
          )
          Spacer(modifier = Modifier.width(6.dp))
          LinearProgressIndicator(
            progress = { topicFraction },
            modifier = Modifier
              .width(54.dp)
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = if (topicFraction == 1f) BrandEmerald else chapter.subject.color,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
          )
        }
      }

      // Expandable Topics Checklist List
      AnimatedVisibility(visible = isExpanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          // Action Bar for chapter checklist
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "CHAPTER TOPICS",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              letterSpacing = 0.8.sp
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              // Mark all
              Text(
                text = if (chapter.isAllTopicsCompleted) "Unmark All" else "Mark All Done",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = chapter.subject.color,
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .clickable { onMarkAllTopics(!chapter.isAllTopicsCompleted) }
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )

              // Add topic
              Text(
                text = "+ Add Topic",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = BrandAccentGold,
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .clickable(onClick = onAddTopicClick)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          // Individual Topic Items
          chapter.topics.forEach { topic ->
            TopicChecklistItem(
              topic = topic,
              subjectColor = chapter.subject.color,
              onToggle = { onToggleTopic(topic.id) }
            )
          }

          Spacer(modifier = Modifier.height(4.dp))
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Key concepts tags (collapsed preview)
      if (!isExpanded) {
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          chapter.keyConcepts.take(3).forEach { concept ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = concept,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(10.dp))
      }

      // PYQ Progress bar & Action buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "PYQs Solved",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "${chapter.pyqsSolved} / ${chapter.totalPyqs}",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          LinearProgressIndicator(
            progress = { pyqFraction },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = chapter.subject.color,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // +5 PYQs quick increment button
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onIncrementPyqs)
            .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
          Text(
            text = "+5 PYQs",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Formulas Button
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BrandPrimary.copy(alpha = 0.15f))
            .clickable(onClick = onViewFormulas)
            .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Formulas",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }
      }
    }
  }
}

@Composable
fun TopicChecklistItem(
  topic: SyllabusTopic,
  subjectColor: Color,
  onToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showHint by remember { mutableStateOf(false) }

  val tagBgColor = when (topic.tag) {
    "PYQ Hotspot" -> BrandAccentGold.copy(alpha = 0.18f)
    "High Yield" -> BrandPrimary.copy(alpha = 0.18f)
    "NCERT Must" -> BrandEmerald.copy(alpha = 0.18f)
    "Lab Tests" -> Color(0xFFAB47BC).copy(alpha = 0.18f)
    "Named Reaction" -> Color(0xFFFF7043).copy(alpha = 0.18f)
    else -> MaterialTheme.colorScheme.surfaceVariant
  }

  val tagTextColor = when (topic.tag) {
    "PYQ Hotspot" -> BrandAccentGold
    "High Yield" -> BrandPrimary
    "NCERT Must" -> BrandEmerald
    "Lab Tests" -> Color(0xFFCE93D8)
    "Named Reaction" -> Color(0xFFFFAB91)
    else -> MaterialTheme.colorScheme.onSurfaceVariant
  }

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .clickable(onClick = onToggle)
      .testTag("topic_item_${topic.id}"),
    shape = RoundedCornerShape(10.dp),
    color = if (topic.isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
    border = BorderStroke(
      1.dp,
      if (topic.isCompleted) BrandEmerald.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Checkbox icon
        IconButton(
          onClick = onToggle,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = if (topic.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = if (topic.isCompleted) "Completed" else "Incomplete",
            tint = if (topic.isCompleted) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Topic Title & Tag
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = topic.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (topic.isCompleted) FontWeight.Normal else FontWeight.Medium,
            color = if (topic.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
            textDecoration = if (topic.isCompleted) TextDecoration.LineThrough else TextDecoration.None
          )

          Spacer(modifier = Modifier.height(4.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Tag Pill
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(tagBgColor)
                .padding(horizontal = 6.dp, vertical = 1.dp)
            ) {
              Text(
                text = topic.tag,
                style = MaterialTheme.typography.labelSmall,
                color = tagTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            }

            if (topic.isHighYield) {
              Text(
                text = "🔥 High Weightage",
                style = MaterialTheme.typography.labelSmall,
                color = BrandAccentGold,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            }

            // Formula Hint Toggle
            if (topic.keyFormulaHint != null) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(BrandPrimary.copy(alpha = 0.12f))
                  .clickable { showHint = !showHint }
                  .padding(horizontal = 6.dp, vertical = 1.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = BrandPrimary,
                    modifier = Modifier.size(11.dp)
                  )
                  Spacer(modifier = Modifier.width(2.dp))
                  Text(
                    text = if (showHint) "Hide Hint" else "Formula Tip",
                    style = MaterialTheme.typography.labelSmall,
                    color = BrandPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                  )
                }
              }
            }
          }
        }
      }

      // Expandable Formula Hint banner
      AnimatedVisibility(visible = showHint && topic.keyFormulaHint != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, start = 34.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(BrandPrimary.copy(alpha = 0.1f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "⚡️ ${topic.keyFormulaHint}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp
          )
        }
      }
    }
  }
}

@Composable
fun FlatTopicChecklistItem(
  chapterName: String,
  classLevel: Int,
  topic: SyllabusTopic,
  subjectColor: Color,
  onToggle: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showHint by remember { mutableStateOf(false) }

  val tagBgColor = when (topic.tag) {
    "PYQ Hotspot" -> BrandAccentGold.copy(alpha = 0.18f)
    "High Yield" -> BrandPrimary.copy(alpha = 0.18f)
    "NCERT Must" -> BrandEmerald.copy(alpha = 0.18f)
    "Lab Tests" -> Color(0xFFAB47BC).copy(alpha = 0.18f)
    "Named Reaction" -> Color(0xFFFF7043).copy(alpha = 0.18f)
    else -> MaterialTheme.colorScheme.surfaceVariant
  }

  val tagTextColor = when (topic.tag) {
    "PYQ Hotspot" -> BrandAccentGold
    "High Yield" -> BrandPrimary
    "NCERT Must" -> BrandEmerald
    "Lab Tests" -> Color(0xFFCE93D8)
    "Named Reaction" -> Color(0xFFFFAB91)
    else -> MaterialTheme.colorScheme.onSurfaceVariant
  }

  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onToggle)
      .testTag("flat_topic_${topic.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.outlinedCardColors(
      containerColor = if (topic.isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f) else MaterialTheme.colorScheme.surface
    ),
    border = BorderStroke(
      1.dp,
      if (topic.isCompleted) BrandEmerald.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Chapter breadcrumb
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(subjectColor.copy(alpha = 0.15f))
              .padding(horizontal = 5.dp, vertical = 1.dp)
          ) {
            Text(
              text = "Class $classLevel",
              style = MaterialTheme.typography.labelSmall,
              color = subjectColor,
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Text(
            text = chapterName,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(tagBgColor)
            .padding(horizontal = 6.dp, vertical = 1.dp)
        ) {
          Text(
            text = topic.tag,
            style = MaterialTheme.typography.labelSmall,
            color = tagTextColor,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Topic Checkbox and Title
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onToggle,
          modifier = Modifier.size(30.dp)
        ) {
          Icon(
            imageVector = if (topic.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = if (topic.isCompleted) "Completed" else "Incomplete",
            tint = if (topic.isCompleted) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(22.dp)
          )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = topic.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (topic.isCompleted) FontWeight.Normal else FontWeight.SemiBold,
            color = if (topic.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
            textDecoration = if (topic.isCompleted) TextDecoration.LineThrough else TextDecoration.None
          )

          if (topic.keyFormulaHint != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { showHint = !showHint }
                .background(BrandPrimary.copy(alpha = 0.1f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = BrandPrimary,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = if (showHint) "Hide formula hint" else "Show formula shortcut",
                style = MaterialTheme.typography.labelSmall,
                color = BrandPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
              )
            }
          }
        }
      }

      AnimatedVisibility(visible = showHint && topic.keyFormulaHint != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, start = 36.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(BrandPrimary.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "⚡️ ${topic.keyFormulaHint}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp
          )
        }
      }
    }
  }
}

@Composable
fun FormulaCardItem(
  card: FormulaCard,
  onToggleBookmark: () -> Unit,
  modifier: Modifier = Modifier
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current

  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("formula_card_${card.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Header: Subject tag & Topic + Bookmark Icon
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(card.subject.color.copy(alpha = 0.15f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = card.subject.displayName,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = card.subject.color
            )
          }

          Spacer(modifier = Modifier.width(6.dp))

          Text(
            text = card.topic,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = {
              clipboardManager.setText(AnnotatedString("${card.title}: ${card.formula}"))
              Toast.makeText(context, "Formula copied to clipboard!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Copy",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(18.dp)
            )
          }

          IconButton(
            onClick = onToggleBookmark,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = if (card.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (card.isBookmarked) BrandAccentGold else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = card.title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      // Formula Box Highlight
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .border(1.dp, card.subject.color.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
          .padding(horizontal = 12.dp, vertical = 10.dp)
      ) {
        Text(
          text = card.formula,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          color = card.subject.color,
          lineHeight = 22.sp
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = card.explanation,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = "💡 ${card.unitsAndConstants}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 11.sp
      )
    }
  }
}

@Composable
fun MentorCardItem(
  mentor: Mentor,
  onViewProfile: () -> Unit,
  onStartChat: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("mentor_card_${mentor.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          // Avatar
          Box(
            modifier = Modifier
              .size(48.dp)
              .clip(CircleShape)
              .background(
                Brush.linearGradient(
                  listOf(BrandPrimary, BrandSecondary)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = mentor.name.take(1),
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color.Black
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = mentor.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(BrandAccentGold.copy(alpha = 0.2f))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "AIR ${mentor.airRank}",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.ExtraBold,
                  color = BrandAccentGold
                )
              }
            }

            Text(
              text = "${mentor.college} • ${mentor.branch}",
              style = MaterialTheme.typography.bodySmall,
              color = BrandPrimary,
              fontWeight = FontWeight.Medium,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = mentor.tagLine,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "💬 \"${mentor.topAdvice}\"",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Rating",
            tint = BrandAccentGold,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${mentor.rating} (${mentor.sessionCount}+ chats)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .clickable(onClick = onViewProfile)
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(
              text = "Strategy",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(BrandPrimary)
              .clickable(onClick = onStartChat)
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Text(
              text = "Ask Doubt 💬",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = Color.Black
            )
          }
        }
      }
    }
  }
}

@Composable
fun CollegeCardItem(
  college: College,
  onViewDetails: () -> Unit,
  onCompare: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("college_card_${college.id}"),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = college.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "📍 ${college.location} • Est. ${college.established}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BrandPrimary.copy(alpha = 0.18f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "NIRF #${college.nirfRank}",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.ExtraBold,
            color = BrandPrimary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Cutoff and Placement Stats Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        StatBadge(title = "Avg Package", value = "₹${college.avgPackageLpa} LPA", color = BrandEmerald)
        StatBadge(title = "CSE Cutoff", value = "AIR < ${college.cseCutoff}", color = BrandAccentGold)
        StatBadge(title = "Mech Cutoff", value = "AIR < ${college.mechCutoff}", color = BrandSecondary)
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = college.overview,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onCompare)
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(
            text = "Compare ⚖️",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BrandPrimary.copy(alpha = 0.2f))
            .clickable(onClick = onViewDetails)
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = "View Branches & Campus",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }
      }
    }
  }
}

@Composable
fun StatBadge(
  title: String,
  value: String,
  color: Color
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      fontSize = 10.sp
    )
    Text(
      text = value,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = FontWeight.Bold,
      color = color
    )
  }
}

@Composable
fun AvengersMotivationCard(
  quote: MotivationalQuote,
  arcReactorEnergy: Int,
  currentStreakDays: Int,
  onNextQuote: () -> Unit,
  onPrevQuote: () -> Unit,
  onRandomQuote: () -> Unit,
  onToggleBookmark: () -> Unit,
  onBoostEnergy: () -> Unit,
  onOpenDeck: () -> Unit,
  modifier: Modifier = Modifier
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current
  val quoteAccentColor = Color(quote.accentColorHex)

  // Animated gradient brush for Avengers heroic atmosphere
  val cardBrush = Brush.linearGradient(
    colors = listOf(
      Color(0xFF141724),
      Color(0xFF1B1E30),
      Color(0xFF0F111A)
    )
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("avengers_motivation_card"),
    shape = RoundedCornerShape(22.dp),
    border = BorderStroke(1.5.dp, quoteAccentColor.copy(alpha = 0.6f)),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(cardBrush)
        .padding(18.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // 1. Header Row: Avengers Protocol & Arc Reactor Energy
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            // Superhero / Avengers Icon Badge
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF23283E))
                .border(1.dp, quoteAccentColor, CircleShape)
                .padding(6.dp),
              contentAlignment = Alignment.Center
            ) {
              val iconRes = when (quote.heroType) {
                "CAPTAIN_AMERICA" -> R.drawable.ic_hero_shield
                "IRON_MAN" -> R.drawable.ic_arc_reactor
                else -> R.drawable.ic_avengers_logo
              }
              Image(
                painter = painterResource(id = iconRes),
                contentDescription = "Avengers Hero Symbol",
                modifier = Modifier.size(24.dp)
              )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "AVENGERS ASSEMBLE",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.ExtraBold,
                  color = quoteAccentColor,
                  letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(BrandAccentGold.copy(alpha = 0.2f))
                    .padding(horizontal = 5.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "${currentStreakDays}D STREAK 🔥",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = BrandAccentGold
                  )
                }
              }
              Text(
                text = "Earth's Mightiest Aspirant Protocol",
                style = MaterialTheme.typography.labelSmall,
                color = Color.LightGray.copy(alpha = 0.7f),
                fontSize = 11.sp
              )
            }
          }

          // Arc Reactor Energy Pill
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF00E5FF).copy(alpha = 0.15f))
              .border(1.dp, Color(0xFF00E5FF).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
              .clickable(onClick = onBoostEnergy)
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = null,
                tint = Color(0xFF00E5FF),
                modifier = Modifier.size(14.dp)
              )
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = "$arcReactorEnergy% Core",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00E5FF),
                fontSize = 11.sp
              )
            }
          }
        }

        // 2. The Motivational Quote Body
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          color = Color(0xFF0A0C14).copy(alpha = 0.75f),
          border = BorderStroke(1.dp, Color.White.copy(alpha = 0.08f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp)
          ) {
            Row(verticalAlignment = Alignment.Top) {
              Text(
                text = "“",
                fontSize = 32.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Black,
                color = quoteAccentColor
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = quote.quote,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 20.sp,
                modifier = Modifier.weight(1f)
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hero / Author Subtitle
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "— ${quote.authorOrHero}",
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                  color = quoteAccentColor
                )
                Text(
                  text = quote.heroRole,
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  color = Color.LightGray.copy(alpha = 0.8f)
                )
              }

              // Copy, Notification & Bookmark Actions
              Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                  onClick = {
                    com.example.notification.NotificationHelper.sendStudyReminder(
                      context = context,
                      reminderType = com.example.data.ReminderType.AVENGERS_BATTLECRY,
                      isTest = true
                    )
                    Toast.makeText(context, "Pushed battlecry to notification bar! 🛡️", Toast.LENGTH_SHORT).show()
                  },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = "Send to Notification",
                    tint = quoteAccentColor,
                    modifier = Modifier.size(16.dp)
                  )
                }

                IconButton(
                  onClick = {
                    clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("\"${quote.quote}\" — ${quote.authorOrHero}"))
                    Toast.makeText(context, "Quote copied to clipboard! ⚡️", Toast.LENGTH_SHORT).show()
                  },
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy Quote",
                    tint = Color.LightGray,
                    modifier = Modifier.size(16.dp)
                  )
                }

                IconButton(
                  onClick = onToggleBookmark,
                  modifier = Modifier.size(32.dp)
                ) {
                  Icon(
                    imageVector = if (quote.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark Quote",
                    tint = if (quote.isBookmarked) quoteAccentColor else Color.LightGray,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }

        // 3. Subtext Tip / JEE Motivation Anchor
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(quoteAccentColor.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = quoteAccentColor,
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = quote.subtext,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.9f)
          )
        }

        // 4. Quick Action Navigation Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF23283E))
                .clickable(onClick = onPrevQuote)
                .padding(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.NavigateBefore,
                contentDescription = "Previous Quote",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF23283E))
                .clickable(onClick = onNextQuote)
                .padding(6.dp)
            ) {
              Icon(
                imageVector = Icons.Default.NavigateNext,
                contentDescription = "Next Quote",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(quoteAccentColor.copy(alpha = 0.2f))
                .clickable(onClick = onRandomQuote)
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = "⚡️ New Battlecry",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = quoteAccentColor
              )
            }
          }

          TextButton(onClick = onOpenDeck) {
            Text(
              text = "View All Quotes 📜",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }
      }
    }
  }
}

@Composable
fun AvengersQuotesDeckDialog(
  quotes: List<MotivationalQuote>,
  selectedCategory: QuoteCategory,
  onSelectCategory: (QuoteCategory) -> Unit,
  onToggleBookmark: (String) -> Unit,
  onDismiss: () -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current

  val filteredQuotes = if (selectedCategory == QuoteCategory.ALL) {
    quotes
  } else {
    quotes.filter { it.category == selectedCategory }
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Image(
            painter = painterResource(id = R.drawable.ic_avengers_logo),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Avengers Motivation Arsenal",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        }
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Category Pills
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          items(QuoteCategory.entries) { category ->
            val isSelected = category == selectedCategory
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) BrandAccentGold else MaterialTheme.colorScheme.surfaceVariant)
                .clickable { onSelectCategory(category) }
                .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
              Text(
                text = "${category.emoji} ${category.label}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        // Quotes Scroll List
        androidx.compose.foundation.lazy.LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .height(380.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(filteredQuotes, key = { it.id }) { item ->
            val accent = Color(item.accentColorHex)
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              border = BorderStroke(1.dp, accent.copy(alpha = 0.4f))
            ) {
              Column(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    val heroIcon = when (item.heroType) {
                      "CAPTAIN_AMERICA" -> R.drawable.ic_hero_shield
                      "IRON_MAN" -> R.drawable.ic_arc_reactor
                      else -> R.drawable.ic_avengers_logo
                    }
                    Image(
                      painter = painterResource(id = heroIcon),
                      contentDescription = null,
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = item.authorOrHero,
                      style = MaterialTheme.typography.labelMedium,
                      fontWeight = FontWeight.Bold,
                      color = accent
                    )
                  }

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                      onClick = {
                        com.example.notification.NotificationHelper.sendStudyReminder(
                          context = context,
                          reminderType = com.example.data.ReminderType.AVENGERS_BATTLECRY,
                          isTest = true
                        )
                        Toast.makeText(context, "Dispatched to notification bar! 🔔", Toast.LENGTH_SHORT).show()
                      },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = "Notify",
                        tint = accent,
                        modifier = Modifier.size(14.dp)
                      )
                    }

                    IconButton(
                      onClick = {
                        clipboardManager.setText(androidx.compose.ui.text.AnnotatedString("\"${item.quote}\" — ${item.authorOrHero}"))
                        Toast.makeText(context, "Quote copied! ⚡️", Toast.LENGTH_SHORT).show()
                      },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                      )
                    }

                    IconButton(
                      onClick = { onToggleBookmark(item.id) },
                      modifier = Modifier.size(28.dp)
                    ) {
                      Icon(
                        imageVector = if (item.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (item.isBookmarked) accent else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                      )
                    }
                  }
                }

                Text(
                  text = "\"${item.quote}\"",
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                  text = item.subtext,
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = onDismiss,
        colors = ButtonDefaults.buttonColors(containerColor = BrandAccentGold)
      ) {
        Text("Assemble & Study!", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    }
  )
}

