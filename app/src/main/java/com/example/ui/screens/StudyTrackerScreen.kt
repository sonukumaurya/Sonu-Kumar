package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SubjectType
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel
import com.example.ui.viewmodel.PomodoroState

@Composable
fun StudyTrackerScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  val minutes = uiState.timerSecondsRemaining / 60
  val seconds = uiState.timerSecondsRemaining % 60
  val formattedTime = String.format("%02d:%02d", minutes, seconds)
  val progressFraction = if (uiState.timerTotalSeconds > 0) {
    uiState.timerSecondsRemaining.toFloat() / uiState.timerTotalSeconds.toFloat()
  } else 1f

  val animatedProgress by animateFloatAsState(
    targetValue = progressFraction,
    label = "pomodoro_progress"
  )

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("study_tracker_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Pomodoro Timer Hero
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("pomodoro_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Duration Presets Row
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            TimerPresetPill(title = "25m Focus", isSelected = uiState.timerTotalSeconds == 25 * 60, onClick = { viewModel.setTimerDuration(25) })
            TimerPresetPill(title = "50m Deep", isSelected = uiState.timerTotalSeconds == 50 * 60, onClick = { viewModel.setTimerDuration(50) })
            TimerPresetPill(title = "5m Break", isSelected = uiState.timerTotalSeconds == 5 * 60, onClick = { viewModel.setTimerDuration(5) })
            TimerPresetPill(title = "15m Rest", isSelected = uiState.timerTotalSeconds == 15 * 60, onClick = { viewModel.setTimerDuration(15) })
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Circular Ring Timer Display
          Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
          ) {
            val trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            val strokeColor = uiState.timerSubject.color

            Canvas(modifier = Modifier.fillMaxSize()) {
              val strokeWidth = 12.dp.toPx()
              val diameter = size.minDimension - strokeWidth
              val radius = diameter / 2f
              val topLeft = Offset((size.width - diameter) / 2f, (size.height - diameter) / 2f)

              // Background track
              drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )

              // Animated progress arc
              drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                topLeft = topLeft,
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
              )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = formattedTime,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = when (uiState.pomodoroState) {
                  PomodoroState.RUNNING -> "🔥 IN FOCUS"
                  PomodoroState.PAUSED -> "⏸️ PAUSED"
                  PomodoroState.BREAK -> "🎉 BREAK TIME"
                  PomodoroState.IDLE -> "READY TO FOCUS"
                },
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (uiState.pomodoroState == PomodoroState.RUNNING) BrandPrimary else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Subject Tagger for session
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            SubjectType.entries.forEach { subject ->
              val isSelected = uiState.timerSubject == subject
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) subject.color else MaterialTheme.colorScheme.surfaceVariant)
                  .clickable { viewModel.setTimerSubjectAndChapter(subject, uiState.timerChapterName) }
                  .padding(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Text(
                  text = subject.displayName,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          // Timer Action Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(
              onClick = { viewModel.resetTimer() },
              modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Reset",
                tint = MaterialTheme.colorScheme.onSurface
              )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
              onClick = {
                if (uiState.pomodoroState == PomodoroState.RUNNING) {
                  viewModel.pauseTimer()
                } else {
                  viewModel.startTimer()
                }
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.pomodoroState == PomodoroState.RUNNING) BrandAccentGold else BrandPrimary
              ),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .height(48.dp)
                .width(140.dp)
                .testTag("timer_toggle_button")
            ) {
              Icon(
                imageVector = if (uiState.pomodoroState == PomodoroState.RUNNING) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (uiState.pomodoroState == PomodoroState.RUNNING) "Pause" else "Start",
                tint = Color.Black
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (uiState.pomodoroState == PomodoroState.RUNNING) "Pause" else "Start",
                color = Color.Black,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // 2. Focus Mindset & Avengers Motivation Banner
    item {
      val quote = uiState.activeQuote
      val quoteAccent = Color(quote.accentColorHex)
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, quoteAccent.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              val heroIcon = when (quote.heroType) {
                "CAPTAIN_AMERICA" -> R.drawable.ic_hero_shield
                "IRON_MAN" -> R.drawable.ic_arc_reactor
                else -> R.drawable.ic_avengers_logo
              }
              Image(
                painter = painterResource(id = heroIcon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "FOCUS MINDSET • ${quote.authorOrHero.uppercase()}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = quoteAccent,
                letterSpacing = 0.8.sp
              )
            }

            IconButton(
              onClick = { viewModel.randomMotivationalQuote() },
              modifier = Modifier.size(28.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "New Quote",
                tint = quoteAccent,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          Text(
            text = "\"${quote.quote}\"",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )

          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(quoteAccent.copy(alpha = 0.12f))
              .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = quoteAccent,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = quote.subtext,
              style = MaterialTheme.typography.labelSmall,
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }
    }

    // 3. Today's Study Stats Card
    item {
      val hours = uiState.todayStudiedMinutes / 60
      val mins = uiState.todayStudiedMinutes % 60
      val targetMins = 360 // 6 hours target
      val progress = (uiState.todayStudiedMinutes.toFloat() / targetMins.toFloat()).coerceIn(0f, 1f)

      OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Today's Study Time",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${hours}h ${mins}m logged today",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Text(
              text = "${(progress * 100).toInt()}% of 6h goal",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = BrandEmerald
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = BrandEmerald,
            trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
          )
        }
      }
    }

    // 3. Weekly Study Distribution Bar Chart
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Weekly Study Hours (Mon - Sun)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(16.dp))

          val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
          val maxHours = 10f

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(130.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
          ) {
            uiState.weeklyStudyHours.forEachIndexed { index, hours ->
              val barHeightFrac = (hours / maxHours).coerceIn(0.1f, 1f)
              val isToday = index == 6

              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
              ) {
                Text(
                  text = "${hours}h",
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isToday) BrandPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                  modifier = Modifier
                    .width(18.dp)
                    .height((barHeightFrac * 90).dp)
                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                    .background(if (isToday) BrandPrimary else BrandSecondary.copy(alpha = 0.6f))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = days.getOrElse(index) { "" },
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }
}

@Composable
fun TimerPresetPill(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(14.dp))
      .background(if (isSelected) BrandPrimary else Color.Transparent)
      .clickable(onClick = onClick)
      .padding(horizontal = 8.dp, vertical = 4.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
