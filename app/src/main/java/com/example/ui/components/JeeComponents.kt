package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.Chapter
import com.example.data.ChapterStatus
import com.example.data.College
import com.example.data.DailyGoal
import com.example.data.FormulaCard
import com.example.data.Mentor
import com.example.data.MockTest
import com.example.data.MotivationalQuote
import com.example.data.QuoteCategory
import com.example.data.SampleData
import com.example.data.SubjectType
import com.example.data.SyllabusTopic
import com.example.data.UserProfile
import com.example.data.WeightageLevel
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CountdownTimerHero(
  targetYear: Int,
  modifier: Modifier = Modifier
) {
  val actualYear = if (targetYear < 2026) 2026 else targetYear

  val examTargets = remember(actualYear) {
    val s1Millis = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.YEAR, actualYear + 1)
      set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY)
      set(java.util.Calendar.DAY_OF_MONTH, 24)
      set(java.util.Calendar.HOUR_OF_DAY, 9)
      set(java.util.Calendar.MINUTE, 0)
      set(java.util.Calendar.SECOND, 0)
      set(java.util.Calendar.MILLISECOND, 0)
    }.timeInMillis

    val s2Millis = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.YEAR, actualYear + 1)
      set(java.util.Calendar.MONTH, java.util.Calendar.APRIL)
      set(java.util.Calendar.DAY_OF_MONTH, 6)
      set(java.util.Calendar.HOUR_OF_DAY, 9)
      set(java.util.Calendar.MINUTE, 0)
      set(java.util.Calendar.SECOND, 0)
      set(java.util.Calendar.MILLISECOND, 0)
    }.timeInMillis

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
  val totalStudyHoursAvailable = days * 8

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
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
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
            text = if (streakDays == 1) "1 Day" else "$streakDays Days",
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

      AnimatedVisibility(visible = isExpanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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

      AnimatedVisibility(visible = showHint && topic.keyFormulaHint != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, start = 34.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BrandPrimary.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          ComponentMathFormula(
            rawFormula = topic.keyFormulaHint ?: "",
            textColor = subjectColor,
            fontSize = 14.sp
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
            .clip(RoundedCornerShape(8.dp))
            .background(BrandPrimary.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          ComponentMathFormula(
            rawFormula = topic.keyFormulaHint ?: "",
            textColor = subjectColor,
            fontSize = 14.sp
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

          if (card.isSavedOffline) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(BrandEmerald.copy(alpha = 0.15f))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = "Offline Cached",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.SemiBold),
                color = BrandEmerald
              )
            }
          }
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

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .border(1.dp, card.subject.color.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
          .padding(horizontal = 14.dp, vertical = 12.dp)
      ) {
        ComponentMathFormula(
          rawFormula = card.formula,
          textColor = card.subject.color,
          fontSize = 18.sp
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = formatComponentMathAnnotated(card.explanation, subScale = 10.sp, italicizeSingleVars = false),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = formatComponentMathAnnotated("💡 ${card.unitsAndConstants}", subScale = 9.sp, italicizeSingleVars = false),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 11.sp
      )
    }
  }
}

// -------------------------------------------------------------
// TEXTBOOK MATH RENDERER FOR COMPONENTS
// -------------------------------------------------------------
private sealed class CompMathSegment {
  data class Plain(val text: String) : CompMathSegment()
  data class Sqrt(val radicand: String) : CompMathSegment()
}

private fun splitCompBySqrt(input: String): List<CompMathSegment> {
  val result = mutableListOf<CompMathSegment>()
  var index = 0
  while (index < input.length) {
    val sqrtPos = input.indexOf("sqrt(", startIndex = index, ignoreCase = true)
    if (sqrtPos == -1) {
      result.add(CompMathSegment.Plain(input.substring(index)))
      break
    }
    if (sqrtPos > index) {
      result.add(CompMathSegment.Plain(input.substring(index, sqrtPos)))
    }
    val openParen = sqrtPos + 4
    var depth = 0
    var closeParen = -1
    for (i in openParen until input.length) {
      when (input[i]) {
        '(' -> depth++
        ')' -> {
          depth--
          if (depth == 0) {
            closeParen = i
            break
          }
        }
      }
    }
    if (closeParen != -1) {
      val inside = input.substring(openParen + 1, closeParen)
      result.add(CompMathSegment.Sqrt(inside))
      index = closeParen + 1
    } else {
      result.add(CompMathSegment.Plain(input.substring(sqrtPos)))
      break
    }
  }
  return result
}

private fun preprocessCompMathText(raw: String): String {
  var s = raw
  s = s.replace("μ_s ", "μ_spin ")
  s = s.replace("μ_s=", "μ_spin=")
  s = s.replace(Regex("--\\(([^)]+)\\)-->"), " ──($1)──► ")
  s = s.replace("-->", " ──► ")
  s = s.replace("->", " → ")
  s = s.replace("<=>", " ⇌ ")
  s = s.replace("<=", " ≤ ")
  s = s.replace(">=", " ≥ ")
  s = s.replace("!=", " ≠ ")
  s = s.replace("+-", "±")
  s = s.replace(Regex("([a-zA-Z0-9_)])\\s*\\*\\s*\\("), "$1(")
  s = s.replace(Regex("\\)\\s*\\*\\s*([a-zA-Z(])"), ") $1")
  s = s.replace(Regex("([0-9])\\s*\\*\\s*([a-zA-ZμλθπΔ])"), "$1$2")
  s = s.replace(" * ", " · ")
  s = s.replace("*", "·")
  s = s.replace("log10", "log_10")
  s = s.replace("log2", "log_2")
  return s
}

fun formatComponentMathAnnotated(
  raw: String,
  subScale: TextUnit = 12.sp,
  italicizeSingleVars: Boolean = true
): AnnotatedString {
  val cleaned = preprocessCompMathText(raw)
  return buildAnnotatedString {
    var i = 0
    while (i < cleaned.length) {
      val ch = cleaned[i]
      if (ch == '_' || ch == '^') {
        val shift = if (ch == '_') BaselineShift.Subscript else BaselineShift.Superscript
        i++
        if (i < cleaned.length && cleaned[i] == '(') {
          val start = i + 1
          var depth = 1
          i++
          while (i < cleaned.length && depth > 0) {
            if (cleaned[i] == '(') depth++
            else if (cleaned[i] == ')') depth--
            i++
          }
          val content = cleaned.substring(start, (i - 1).coerceAtLeast(start))
          withStyle(SpanStyle(baselineShift = shift, fontSize = subScale, fontStyle = FontStyle.Normal)) {
            append(content)
          }
        } else {
          val start = i
          while (i < cleaned.length && (cleaned[i].isLetterOrDigit() || cleaned[i] == '/' || cleaned[i] == '-' || cleaned[i] == '+')) {
            if ((cleaned[i] == '-' || cleaned[i] == '+') && i > start) break
            i++
          }
          val content = cleaned.substring(start, i)
          withStyle(SpanStyle(baselineShift = shift, fontSize = subScale, fontStyle = FontStyle.Normal)) {
            append(content)
          }
        }
      } else if (ch.isDigit() && i > 0 && cleaned[i - 1].isLetter() && (i < 2 || !cleaned[i - 2].isDigit())) {
        val start = i
        while (i < cleaned.length && cleaned[i].isDigit()) {
          i++
        }
        withStyle(SpanStyle(baselineShift = BaselineShift.Subscript, fontSize = subScale, fontStyle = FontStyle.Normal)) {
          append(cleaned.substring(start, i))
        }
      } else if (italicizeSingleVars && (ch.isLetter() || ch == 'μ' || ch == 'λ' || ch == 'θ')) {
        val start = i
        while (i < cleaned.length && cleaned[i].isLetter()) {
          i++
        }
        val word = cleaned.substring(start, i)
        val isSingleVar = word.length == 1 && word[0] !in listOf('R', 'C', 'H', 'O', 'N', 'S', 'P', 'K', 'I', 'F', 'B', 'V', 'W', 'U', 'Y')
        if (isSingleVar || word == "μ") {
          withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
            append(word)
          }
        } else {
          append(word)
        }
      } else {
        append(ch)
        i++
      }
    }
  }
}

@Composable
fun ComponentMathFormula(
  rawFormula: String,
  textColor: Color,
  fontSize: TextUnit = 17.sp,
  modifier: Modifier = Modifier
) {
  val equations = remember(rawFormula) {
    rawFormula.split(" & ").map { it.trim() }.filter { it.isNotEmpty() }
  }
  val subSize = (fontSize.value * 0.68f).sp

  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    equations.forEach { eq ->
      val segments = remember(eq) { splitCompBySqrt(eq) }
      Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
      ) {
        segments.forEach { segment ->
          when (segment) {
            is CompMathSegment.Plain -> {
              Text(
                text = formatComponentMathAnnotated(segment.text, subScale = subSize, italicizeSingleVars = true),
                fontFamily = FontFamily.Serif,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                color = textColor,
                lineHeight = (fontSize.value * 1.3f).sp
              )
            }
            is CompMathSegment.Sqrt -> {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "√",
                  fontFamily = FontFamily.Serif,
                  fontSize = (fontSize.value * 1.18f).sp,
                  fontWeight = FontWeight.Normal,
                  color = textColor
                )
                Box(
                  modifier = Modifier
                    .drawBehind {
                      val stroke = 1.5.dp.toPx()
                      val y = 2.dp.toPx()
                      drawLine(
                        color = textColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = stroke
                      )
                    }
                    .padding(top = 3.dp, start = 2.dp, end = 3.dp)
                ) {
                  Text(
                    text = formatComponentMathAnnotated(segment.radicand, subScale = subSize, italicizeSingleVars = true),
                    fontFamily = FontFamily.Serif,
                    fontSize = fontSize,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                  )
                }
              }
            }
          }
        }
      }
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

// -------------------------------------------------------------
// DOCTOR STRANGE CHARACTER PORTRAIT + CLOAK OF LEVITATION & MANDALA
// -------------------------------------------------------------
@Composable
fun AvengersThemeArtWatermark(
  heroType: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  val mysticGold = Color(0xFFFFB300)
  val mysticAmber = Color(0xFFFF6D00)
  val mysticEmerald = Color(0xFF00E676)
  val cloakRed = Color(0xFFC62828)
  val cloakDarkRed = Color(0xFF8E0000)
  val tunicNavy = Color(0xFF1A237E)

  Box(
    modifier = modifier.size(250.dp),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.size(240.dp)) {
      val cx = size.width * 0.56f
      val cy = size.height * 0.46f
      val maxR = size.minDimension * 0.46f

      // 1. Ambient Portal Glow behind Doctor Strange
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            mysticGold.copy(alpha = 0.34f),
            mysticAmber.copy(alpha = 0.16f),
            Color.Transparent
          ),
          center = Offset(cx, cy),
          radius = maxR * 1.15f
        ),
        radius = maxR * 1.15f,
        center = Offset(cx, cy)
      )

      // Outer Eldritch Mandala Ring
      drawCircle(
        color = mysticGold.copy(alpha = 0.48f),
        radius = maxR * 0.94f,
        center = Offset(cx, cy),
        style = Stroke(
          width = 2.dp.toPx(),
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 8f), 0f)
        )
      )

      // Interlocking Sacred Geometry Squares behind Doctor Strange
      val sqSide = maxR * 1.16f
      val topLeft = Offset(cx - sqSide / 2f, cy - sqSide / 2f)
      for (angle in listOf(0f, 30f, 60f)) {
        rotate(degrees = angle, pivot = Offset(cx, cy)) {
          drawRect(
            color = mysticGold.copy(alpha = 0.30f),
            topLeft = topLeft,
            size = Size(sqSide, sqSide),
            style = Stroke(width = 1.3.dp.toPx())
          )
        }
      }

      // 2. DOCTOR STRANGE CHARACTER FIGURE (Cloak of Levitation + Head + Eye of Agamotto)
      // High-Collared Red Cloak of Levitation (Outer Cape & Iconic High Winged Collar)
      val cloakPath = Path().apply {
        // Left high collar tip
        moveTo(cx - maxR * 0.44f, cy - maxR * 0.38f)
        // Left outer collar flare
        lineTo(cx - maxR * 0.54f, cy - maxR * 0.08f)
        // Left sweeping shoulder & cape
        lineTo(cx - maxR * 0.82f, cy + maxR * 0.25f)
        lineTo(cx - maxR * 0.95f, cy + maxR * 1.05f)
        // Bottom hem of cape
        lineTo(cx + maxR * 0.95f, cy + maxR * 1.05f)
        // Right sweeping shoulder & cape
        lineTo(cx + maxR * 0.82f, cy + maxR * 0.25f)
        // Right outer collar flare
        lineTo(cx + maxR * 0.54f, cy - maxR * 0.08f)
        // Right high collar tip
        lineTo(cx + maxR * 0.44f, cy - maxR * 0.38f)
        // Inner collar dip behind neck
        lineTo(cx + maxR * 0.22f, cy - maxR * 0.12f)
        lineTo(cx - maxR * 0.22f, cy - maxR * 0.12f)
        close()
      }
      drawPath(
        path = cloakPath,
        brush = Brush.verticalGradient(
          colors = listOf(
            cloakRed.copy(alpha = 0.62f),
            cloakDarkRed.copy(alpha = 0.48f),
            Color.Transparent
          ),
          startY = cy - maxR * 0.4f,
          endY = cy + maxR * 1.05f
        )
      )
      // Gold trim along the Cloak of Levitation collar
      drawPath(
        path = cloakPath,
        color = mysticGold.copy(alpha = 0.45f),
        style = Stroke(width = 1.4.dp.toPx())
      )

      // Inner Sanctum Tunic (Deep Navy Robe over Torso)
      val tunicPath = Path().apply {
        moveTo(cx - maxR * 0.36f, cy + maxR * 0.12f)
        lineTo(cx + maxR * 0.36f, cy + maxR * 0.12f)
        lineTo(cx + maxR * 0.48f, cy + maxR * 0.98f)
        lineTo(cx - maxR * 0.48f, cy + maxR * 0.98f)
        close()
      }
      drawPath(
        path = tunicPath,
        color = tunicNavy.copy(alpha = 0.65f)
      )

      // Doctor Strange Head & Hair Silhouette
      val headCenter = Offset(cx, cy - maxR * 0.24f)
      val headRadius = maxR * 0.21f
      // Hair & Head base
      drawCircle(
        color = Color(0xFF1E1B2E).copy(alpha = 0.82f),
        radius = headRadius,
        center = headCenter
      )
      // Iconic white/silver hair streaks at both temples
      drawArc(
        color = Color.White.copy(alpha = 0.65f),
        startAngle = 155f,
        sweepAngle = 35f,
        useCenter = false,
        topLeft = Offset(headCenter.x - headRadius, headCenter.y - headRadius),
        size = Size(headRadius * 2f, headRadius * 2f),
        style = Stroke(width = 2.5.dp.toPx())
      )
      drawArc(
        color = Color.White.copy(alpha = 0.65f),
        startAngle = 350f,
        sweepAngle = 35f,
        useCenter = false,
        topLeft = Offset(headCenter.x - headRadius, headCenter.y - headRadius),
        size = Size(headRadius * 2f, headRadius * 2f),
        style = Stroke(width = 2.5.dp.toPx())
      )
      // Glowing Mystic Eyes (Sorcerer Supreme Ascended State)
      drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = 0.9f),
        radius = 2.6.dp.toPx(),
        center = Offset(headCenter.x - headRadius * 0.36f, headCenter.y - headRadius * 0.05f)
      )
      drawCircle(
        color = Color(0xFF00E5FF).copy(alpha = 0.9f),
        radius = 2.6.dp.toPx(),
        center = Offset(headCenter.x + headRadius * 0.36f, headCenter.y - headRadius * 0.05f)
      )

      // 3. Glowing Eye of Agamotto (Time Stone Amulet on Chest)
      val eyeCenter = Offset(cx, cy + maxR * 0.28f)
      // Amulet cords
      drawLine(
        color = mysticGold.copy(alpha = 0.55f),
        start = Offset(cx - maxR * 0.16f, cy + maxR * 0.04f),
        end = eyeCenter,
        strokeWidth = 1.6.dp.toPx()
      )
      drawLine(
        color = mysticGold.copy(alpha = 0.55f),
        start = Offset(cx + maxR * 0.16f, cy + maxR * 0.04f),
        end = eyeCenter,
        strokeWidth = 1.6.dp.toPx()
      )
      // Emerald Time Stone Aura
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            mysticEmerald.copy(alpha = 0.85f),
            mysticEmerald.copy(alpha = 0.25f),
            Color.Transparent
          ),
          center = eyeCenter,
          radius = maxR * 0.24f
        ),
        radius = maxR * 0.24f,
        center = eyeCenter
      )
      // Golden Eye of Agamotto Frame
      drawCircle(
        color = mysticGold.copy(alpha = 0.85f),
        radius = maxR * 0.09f,
        center = eyeCenter,
        style = Stroke(width = 1.8.dp.toPx())
      )
      // Bright green Time Stone core
      drawCircle(
        color = mysticEmerald,
        radius = maxR * 0.045f,
        center = eyeCenter
      )

      // 4. Two Smaller Eldritch Magic Shields on Left & Right Hands
      val leftHand = Offset(cx - maxR * 0.62f, cy + maxR * 0.42f)
      val rightHand = Offset(cx + maxR * 0.62f, cy + maxR * 0.42f)
      for (handCenter in listOf(leftHand, rightHand)) {
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(
              mysticGold.copy(alpha = 0.45f),
              mysticAmber.copy(alpha = 0.15f),
              Color.Transparent
            ),
            center = handCenter,
            radius = maxR * 0.28f
          ),
          radius = maxR * 0.28f,
          center = handCenter
        )
        drawCircle(
          color = mysticGold.copy(alpha = 0.65f),
          radius = maxR * 0.20f,
          center = handCenter,
          style = Stroke(width = 1.3.dp.toPx())
        )
      }
    }
  }
}

// -------------------------------------------------------------
// USER AVATAR VIEW WITH CUSTOM PHOTO & PRESET SUPPORT
// -------------------------------------------------------------
@Composable
fun UserAvatarView(
  userProfile: UserProfile,
  size: androidx.compose.ui.unit.Dp = 48.dp,
  isEditable: Boolean = false,
  onClick: (() -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val initials = remember(userProfile.name) {
    userProfile.name.trim()
      .split(" ")
      .filter { it.isNotBlank() }
      .mapNotNull { it.firstOrNull()?.toString() }
      .take(2)
      .joinToString("")
      .ifEmpty { "SK" }
      .uppercase()
  }

  val auraColor = Color(userProfile.avatarColorHex)

  val avatarBoxModifier = modifier
    .size(size)
    .clip(CircleShape)
    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
    .border(2.dp, auraColor, CircleShape)

  Box(
    modifier = Modifier.size(if (isEditable) size + 6.dp else size),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = avatarBoxModifier,
      contentAlignment = Alignment.Center
    ) {
      if (!userProfile.avatarUri.isNullOrBlank()) {
        AsyncImage(
          model = userProfile.avatarUri,
          contentDescription = "User Profile Photo",
          modifier = Modifier
            .size(size)
            .clip(CircleShape),
          contentScale = ContentScale.Crop
        )
      } else {
        val presetDrawable = when (userProfile.avatarPreset) {
          "iron_man" -> R.drawable.ic_arc_reactor
          "cap" -> R.drawable.ic_hero_shield
          "thor" -> R.drawable.ic_thor_hammer
          "strange" -> R.drawable.ic_doctor_strange
          "spiderman" -> R.drawable.ic_spiderman
          "infinity_gauntlet" -> R.drawable.ic_infinity_gauntlet
          "avengers_assemble" -> R.drawable.ic_avengers_logo
          else -> null
        }

        if (presetDrawable != null) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                Brush.radialGradient(
                  listOf(Color(0xFF1E2235), Color(0xFF0F111A))
                )
              )
              .padding(size * 0.16f),
            contentAlignment = Alignment.Center
          ) {
            Image(
              painter = painterResource(id = presetDrawable),
              contentDescription = "Avatar Preset",
              modifier = Modifier.size(size * 0.72f),
              contentScale = ContentScale.Fit
            )
          }
        } else if (userProfile.avatarPreset == "aspirant_boy") {
          Box(
            modifier = Modifier
              .size(size)
              .background(Brush.linearGradient(listOf(BrandEmerald, BrandPrimary))),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "👨🎓", fontSize = (size.value * 0.48f).sp)
          }
        } else if (userProfile.avatarPreset == "aspirant_girl") {
          Box(
            modifier = Modifier
              .size(size)
              .background(Brush.linearGradient(listOf(BrandRose, BrandAccentGold))),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "👩🎓", fontSize = (size.value * 0.48f).sp)
          }
        } else {
          Box(
            modifier = Modifier
              .size(size)
              .background(
                Brush.linearGradient(
                  listOf(BrandPrimary, BrandSecondary)
                )
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = initials,
              fontSize = (size.value * 0.38f).sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.Black
            )
          }
        }
      }
    }

    if (isEditable) {
      Box(
        modifier = Modifier
          .size(size * 0.38f)
          .align(Alignment.BottomEnd)
          .clip(CircleShape)
          .background(BrandAccentGold)
          .border(1.dp, Color.Black, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.CameraAlt,
          contentDescription = "Edit Photo",
          tint = Color.Black,
          modifier = Modifier.size(size * 0.22f)
        )
      }
    }
  }
}

// -------------------------------------------------------------
// EDIT PROFILE AVATAR DIALOG (GALLERY PICKER, URL & HERO PRESETS)
// -------------------------------------------------------------
@Composable
fun EditProfileAvatarDialog(
  userProfile: UserProfile,
  onSaveAvatar: (customUri: String?, presetId: String, colorHex: Long, clearCustom: Boolean) -> Unit,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  var selectedTab by remember { mutableStateOf(0) }
  var selectedPreset by remember { mutableStateOf(userProfile.avatarPreset) }
  var customImageUrl by remember { mutableStateOf(userProfile.avatarUri ?: "") }
  var selectedColorHex by remember { mutableStateOf(userProfile.avatarColorHex) }
  var hasNewPickedImage by remember { mutableStateOf(false) }

  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri ->
    if (uri != null) {
      customImageUrl = uri.toString()
      hasNewPickedImage = true
      Toast.makeText(context, "Photo loaded from device! 📸", Toast.LENGTH_SHORT).show()
    }
  }

  val previewProfile = remember(userProfile, customImageUrl, selectedPreset, selectedColorHex) {
    userProfile.copy(
      avatarUri = customImageUrl.trim().ifEmpty { null },
      avatarPreset = selectedPreset,
      avatarColorHex = selectedColorHex
    )
  }

  val colorPalette = listOf(
    0xFF00E5FF,
    0xFFFFD700,
    0xFF00E676,
    0xFF42A5F5,
    0xFFFF1744,
    0xFFAB47BC,
    0xFFFF9100
  )

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.CameraAlt,
          contentDescription = null,
          tint = BrandPrimary,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Custom User Profile Photo",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .height(420.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
          border = BorderStroke(1.dp, Color(selectedColorHex).copy(alpha = 0.6f))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            UserAvatarView(
              userProfile = previewProfile,
              size = 56.dp,
              isEditable = false
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = previewProfile.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = if (!previewProfile.avatarUri.isNullOrBlank()) "Custom Photo Active 📸" else "Theme: ${selectedPreset.replace("_", " ").uppercase()}",
                style = MaterialTheme.typography.labelSmall,
                color = Color(selectedColorHex),
                fontWeight = FontWeight.SemiBold
              )
            }
            if (!customImageUrl.isNullOrBlank()) {
              IconButton(
                onClick = {
                  customImageUrl = ""
                  hasNewPickedImage = false
                },
                modifier = Modifier.size(32.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.DeleteOutline,
                  contentDescription = "Remove Custom Photo",
                  tint = BrandRose,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }
        }

        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = MaterialTheme.colorScheme.surface,
          contentColor = BrandPrimary
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            text = { Text("Avengers Presets", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            text = { Text("Upload Photo", fontSize = 12.sp, fontWeight = FontWeight.Bold) }
          )
        }

        if (selectedTab == 0) {
          androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(SampleData.avatarPresets) { preset ->
              val isSelected = selectedPreset == preset.id && customImageUrl.isBlank()
              val presetColor = Color(preset.themeColorHex)

              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(12.dp))
                  .clickable {
                    selectedPreset = preset.id
                    selectedColorHex = preset.themeColorHex
                    customImageUrl = ""
                  },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                  containerColor = if (isSelected) presetColor.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                  if (isSelected) 2.dp else 1.dp,
                  if (isSelected) presetColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(38.dp)
                      .clip(CircleShape)
                      .background(presetColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                  ) {
                    if (preset.drawableRes != null) {
                      Image(
                        painter = painterResource(id = preset.drawableRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                      )
                    } else {
                      Text(text = preset.emoji, fontSize = 20.sp)
                    }
                  }

                  Spacer(modifier = Modifier.width(12.dp))

                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = preset.label,
                      style = MaterialTheme.typography.labelLarge,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                      text = preset.subtitle,
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      fontSize = 11.sp
                    )
                  }

                  if (isSelected) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = "Selected",
                      tint = presetColor,
                      modifier = Modifier.size(20.dp)
                    )
                  }
                }
              }
            }
          }
        } else {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Button(
              onClick = {
                photoPickerLauncher.launch("image/*")
              },
              colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("pick_gallery_photo_button")
            ) {
              Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("Choose Photo from Device Gallery", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Text(
              text = "— OR PASTE IMAGE URL —",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.align(Alignment.CenterHorizontally),
              fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
              value = customImageUrl,
              onValueChange = {
                customImageUrl = it
              },
              label = { Text("Profile Image URL (https://...)") },
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Link,
                  contentDescription = null,
                  tint = BrandPrimary
                )
              },
              singleLine = true,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_image_url_field")
            )

            Text(
              text = "💡 Tip: You can select any photo from your phone's storage or paste a direct image URL to personalize your aspirant avatar.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              fontSize = 11.sp
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Avatar Aura Ring:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            colorPalette.forEach { hex ->
              val color = Color(hex)
              val isSelected = selectedColorHex == hex
              Box(
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(color)
                  .border(
                    if (isSelected) 2.5.dp else 1.dp,
                    if (isSelected) Color.White else Color.Transparent,
                    CircleShape
                  )
                  .clickable { selectedColorHex = hex }
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          val finalUri = customImageUrl.trim().ifEmpty { null }
          onSaveAvatar(
            finalUri,
            selectedPreset,
            selectedColorHex,
            finalUri == null && userProfile.avatarUri != null
          )
          Toast.makeText(context, "Profile photo updated! ✨", Toast.LENGTH_SHORT).show()
          onDismiss()
        },
        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
        modifier = Modifier.testTag("save_avatar_dialog_button")
      ) {
        Text("Apply Avatar", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

// -------------------------------------------------------------
// UPGRADED AVENGERS & DOCTOR STRANGE HERO CARD WITH CHARACTER IMAGE BG
// -------------------------------------------------------------
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

  // Persistent custom Doctor Strange background image URI in SharedPreferences
  val bgPrefs = remember { context.getSharedPreferences("jee_hero_bg_prefs", Context.MODE_PRIVATE) }
  val defaultDoctorStrangeImageUrl = "https://cdn.jsdelivr.net/gh/akabab/superhero-api@0.3.0/api/images/lg/226-doctor-strange.jpg"
  var customBgUri by remember {
    mutableStateOf(bgPrefs.getString("doctor_strange_bg_uri", defaultDoctorStrangeImageUrl) ?: defaultDoctorStrangeImageUrl)
  }

  val bgPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument()
  ) { uri ->
    if (uri != null) {
      try {
        context.contentResolver.takePersistableUriPermission(
          uri,
          Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
      } catch (_: Exception) {}
      customBgUri = uri.toString()
      bgPrefs.edit().putString("doctor_strange_bg_uri", uri.toString()).apply()
      Toast.makeText(context, "Doctor Strange background updated! 🌀", Toast.LENGTH_SHORT).show()
    }
  }

  val cardBrush = Brush.linearGradient(
    colors = listOf(
      Color(0xFF140D26),
      Color(0xFF1C1335),
      Color(0xFF0A0D18)
    )
  )

  val heroIconRes = when (quote.heroType) {
    "CAPTAIN_AMERICA" -> R.drawable.ic_hero_shield
    "IRON_MAN" -> R.drawable.ic_arc_reactor
    "THOR" -> R.drawable.ic_thor_hammer
    "DOCTOR_STRANGE" -> R.drawable.ic_doctor_strange
    "SPIDER_MAN" -> R.drawable.ic_spiderman
    "INFINITY_GAUNTLET" -> R.drawable.ic_infinity_gauntlet
    else -> R.drawable.ic_doctor_strange
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("avengers_motivation_card"),
    shape = RoundedCornerShape(22.dp),
    border = BorderStroke(1.5.dp, Color(0xFFFFB300).copy(alpha = 0.78f)),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(cardBrush)
    ) {
      // 0A. DOCTOR STRANGE CHARACTER ILLUSTRATION + CLOAK OF LEVITATION (Offline Fallback & Aura)
      AvengersThemeArtWatermark(
        heroType = quote.heroType,
        accentColor = quoteAccentColor,
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(top = 0.dp, end = 0.dp)
      )

      // 0B. FULL-BLEED DOCTOR STRANGE PHOTO BACKGROUND (Online HD + Custom Gallery Photo)
      AsyncImage(
        model = customBgUri,
        contentDescription = "Doctor Strange Character Background",
        modifier = Modifier
          .matchParentSize()
          .alpha(0.42f),
        contentScale = ContentScale.Crop
      )

      // 0C. Dark Vignette Scrim so text stays crisp over Doctor Strange's photo
      Box(
        modifier = Modifier
          .matchParentSize()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                Color(0xFF0B0914).copy(alpha = 0.35f),
                Color(0xFF0B0914).copy(alpha = 0.55f),
                Color(0xFF08070F).copy(alpha = 0.82f)
              )
            )
          )
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // 1. Header Row: Doctor Strange Emblem + Title + Clean Non-Wrapping Streak/Core Pills
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Tappable Doctor Strange Emblem (Tap to pick any custom Doctor Strange wallpaper!)
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF241938))
                .border(1.5.dp, Color(0xFFFFB300), CircleShape)
                .clickable { bgPickerLauncher.launch(arrayOf("image/*")) }
                .padding(7.dp),
              contentAlignment = Alignment.Center
            ) {
              Image(
                painter = painterResource(id = R.drawable.ic_doctor_strange),
                contentDescription = "Doctor Strange Mystic Symbol",
                modifier = Modifier.size(28.dp)
              )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "AVENGERS ASSEMBLE",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFFFFB300),
                letterSpacing = 0.8.sp,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Doctor Strange • Mystic Protocol",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          // Right side: Streak Pill & Core Energy Pill stacked cleanly
          Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp)
          ) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(BrandAccentGold.copy(alpha = 0.24f))
                .border(1.dp, BrandAccentGold.copy(alpha = 0.65f), RoundedCornerShape(10.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "🔥 ${currentStreakDays}D STREAK",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = BrandAccentGold,
                maxLines = 1,
                softWrap = false
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF00E5FF).copy(alpha = 0.18f))
                .border(1.dp, Color(0xFF00E5FF).copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                .clickable(onClick = onBoostEnergy)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.FlashOn,
                  contentDescription = null,
                  tint = Color(0xFF00E5FF),
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                  text = "$arcReactorEnergy% Core",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF00E5FF),
                  fontSize = 10.sp,
                  maxLines = 1,
                  softWrap = false
                )
              }
            }
          }
        }

        // 2. The Motivational Quote Body (Semi-transparent so Doctor Strange shows through!)
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFF0B0A16).copy(alpha = 0.68f),
          border = BorderStroke(1.dp, Color(0xFFFFB300).copy(alpha = 0.42f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp)
          ) {
            Row(verticalAlignment = Alignment.Top) {
              Text(
                text = "“",
                fontSize = 34.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFB300)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = quote.quote,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                lineHeight = 21.sp,
                modifier = Modifier.weight(1f)
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Image(
                    painter = painterResource(id = heroIconRes),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(
                    text = quote.authorOrHero,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = quoteAccentColor
                  )
                }
                Text(
                  text = quote.heroRole,
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  color = Color.LightGray.copy(alpha = 0.85f)
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
                    clipboardManager.setText(AnnotatedString("\"${quote.quote}\" — ${quote.authorOrHero}"))
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
            .background(Color(0xFFFFB300).copy(alpha = 0.16f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null,
            tint = Color(0xFFFFB300),
            modifier = Modifier.size(15.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = quote.subtext,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = Color.White.copy(alpha = 0.95f)
          )
        }

        // 4. Quick Action Navigation Row + Custom BG Picker + Theme Arsenal
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

            Spacer(modifier = Modifier.width(5.dp))

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

            Spacer(modifier = Modifier.width(6.dp))

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(quoteAccentColor.copy(alpha = 0.24f))
                .clickable(onClick = onRandomQuote)
                .padding(horizontal = 9.dp, vertical = 6.dp)
            ) {
              Text(
                text = "⚡️ Next",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = quoteAccentColor,
                maxLines = 1,
                softWrap = false
              )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Pick custom Doctor Strange photo from gallery button
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFB300).copy(alpha = 0.22f))
                .clickable { bgPickerLauncher.launch(arrayOf("image/*")) }
                .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
              Text(
                text = "🖼️ BG",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFB300),
                maxLines = 1,
                softWrap = false
              )
            }
          }

          TextButton(
            onClick = onOpenDeck,
            contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp)
          ) {
            Text(
              text = "Theme Arsenal 📜",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              maxLines = 1,
              softWrap = false
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
            painter = painterResource(id = R.drawable.ic_doctor_strange),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
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

        androidx.compose.foundation.lazy.LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .height(380.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(filteredQuotes, key = { it.id }) { item ->
            val accent = Color(item.accentColorHex)
            val heroIcon = when (item.heroType) {
              "CAPTAIN_AMERICA" -> R.drawable.ic_hero_shield
              "IRON_MAN" -> R.drawable.ic_arc_reactor
              "THOR" -> R.drawable.ic_thor_hammer
              "DOCTOR_STRANGE" -> R.drawable.ic_doctor_strange
              "SPIDER_MAN" -> R.drawable.ic_spiderman
              "INFINITY_GAUNTLET" -> R.drawable.ic_infinity_gauntlet
              else -> R.drawable.ic_avengers_logo
            }

            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
              border = BorderStroke(1.dp, accent.copy(alpha = 0.5f))
            ) {
              Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                  painter = painterResource(id = heroIcon),
                  contentDescription = null,
                  modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.BottomEnd)
                    .padding(end = 6.dp, bottom = 6.dp)
                    .alpha(0.12f),
                  contentScale = ContentScale.Fit
                )

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
                    Row(
                      modifier = Modifier.weight(1f),
                      verticalAlignment = Alignment.CenterVertically
                    ) {
                      Image(
                        painter = painterResource(id = heroIcon),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
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
                          clipboardManager.setText(AnnotatedString("\"${item.quote}\" — ${item.authorOrHero}"))
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
