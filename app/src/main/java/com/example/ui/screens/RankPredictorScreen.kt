package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CandidateCategory
import com.example.data.ExamTypeMode
import com.example.data.SampleData
import com.example.data.ScoreEntryMode
import com.example.data.ShiftDifficulty
import com.example.data.SubjectQuestionBreakdown
import com.example.data.SubjectType
import com.example.data.TargetGoalPreset
import com.example.data.WhatIfScenario
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel
import java.util.Locale

@Composable
fun RankPredictorScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Rank Predictor, 1: Score Simulator

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("rank_predictor_screen")
  ) {
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = BrandPrimary,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
          color = BrandPrimary,
          height = 3.dp
        )
      }
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        modifier = Modifier.testTag("tab_rank_predictor"),
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Rank Predictor", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium)
          }
        }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        modifier = Modifier.testTag("tab_score_simulator"),
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Score Simulator", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium)
          }
        }
      )
    }

    if (selectedTab == 0) {
      RankPredictorTabContent(uiState = uiState, viewModel = viewModel)
    } else {
      ScoreSimulatorTabContent(uiState = uiState, viewModel = viewModel)
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RankPredictorTabContent(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel
) {
  var selectedCollegeFilter by remember { mutableStateOf("ALL") } // ALL, IITS, NITS, IIITS, BITS

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Controls Header: Exam Mode & Category & Shift Chips
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Exam Mode Selector (JEE Main vs Advanced)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Exam Target",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              ExamTypeMode.entries.forEach { mode ->
                val isSelected = uiState.examMode == mode
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { viewModel.setExamTypeMode(mode) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = mode.label,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }
          }

          // Category Selector Chips
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "Reservation Category",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              items(CandidateCategory.entries) { cat ->
                val isSelected = uiState.candidateCategory == cat
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) BrandEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                      1.dp,
                      if (isSelected) BrandEmerald else Color.Transparent,
                      RoundedCornerShape(10.dp)
                    )
                    .clickable { viewModel.setCandidateCategory(cat) }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                  Text(
                    text = cat.shortCode,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) BrandEmerald else MaterialTheme.colorScheme.onSurface
                  )
                }
              }
            }
          }

          // Shift Difficulty Selector
          if (uiState.examMode == ExamTypeMode.JEE_MAIN) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Shift Normalization",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                  text = uiState.shiftDifficulty.label,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = BrandAccentGold
                )
              }
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                ShiftDifficulty.entries.forEach { shift ->
                  val isSelected = uiState.shiftDifficulty == shift
                  Box(
                    modifier = Modifier
                      .weight(1f)
                      .clip(RoundedCornerShape(8.dp))
                      .background(if (isSelected) BrandAccentGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)
                      .border(1.dp, if (isSelected) BrandAccentGold else Color.Transparent, RoundedCornerShape(8.dp))
                      .clickable { viewModel.setShiftDifficulty(shift) }
                      .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = shift.label,
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                      color = if (isSelected) BrandAccentGold else MaterialTheme.colorScheme.onSurface,
                      textAlign = TextAlign.Center
                    )
                  }
                }
              }
            }
          }
        }
      }
    }

    // 2. Giant Predicted Output Hero Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("predicted_rank_banner"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, BrandPrimary.copy(alpha = 0.6f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = null,
              tint = BrandPrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "PREDICTED ${uiState.examMode.shortCode} RANK & PERCENTILE",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.ExtraBold,
              color = BrandPrimary,
              letterSpacing = 0.8.sp
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Score / Percentile / AIR Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Percentile Block
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "${uiState.predictedPercentile}%ile",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = BrandEmerald
              )
              Text(
                text = "Estimated %ile",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            Box(
              modifier = Modifier
                .width(1.dp)
                .height(48.dp)
                .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            )

            // AIR Block
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "AIR ~${uiState.predictedAir}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = BrandAccentGold
              )
              Text(
                text = "All India Rank (CRL)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          if (uiState.candidateCategory != CandidateCategory.OPEN_CRL) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(BrandPrimary.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text(
                text = "${uiState.candidateCategory.shortCode} Category Rank: ~${uiState.predictedCategoryRank}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = BrandPrimary
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Cutoff Qualifier Status & Total Score Pill
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (uiState.isAdvQualified) BrandEmerald.copy(alpha = 0.12f) else BrandRose.copy(alpha = 0.12f))
              .border(
                1.dp,
                if (uiState.isAdvQualified) BrandEmerald.copy(alpha = 0.4f) else BrandRose.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
              )
              .padding(horizontal = 12.dp, vertical = 10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = if (uiState.isAdvQualified) Icons.Default.CheckCircle else Icons.Default.HelpOutline,
                  contentDescription = null,
                  tint = if (uiState.isAdvQualified) BrandEmerald else BrandRose,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (uiState.isAdvQualified) "Qualified for Advanced (Cutoff: ${uiState.advQualifyCutoffScore}M)" else "Below Cutoff (${uiState.advQualifyCutoffScore}M)",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = if (uiState.isAdvQualified) BrandEmerald else BrandRose
                )
              }

              Text(
                text = "${uiState.totalScore}/${uiState.maxPossibleScore} Marks",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }
    }

    // 3. Mark Entry Mode Selector (Sliders vs Question Breakdown)
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Mock Score Input",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(2.dp)
        ) {
          ScoreEntryMode.entries.forEach { mode ->
            val isSelected = uiState.scoreEntryMode == mode
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) BrandPrimary else Color.Transparent)
                .clickable { viewModel.setScoreEntryMode(mode) }
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = mode.label,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }
    }

    // 4. Score Input Component (Sliders or Question Breakdown)
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          if (uiState.scoreEntryMode == ScoreEntryMode.QUICK_SLIDERS) {
            val maxScore = if (uiState.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100

            ScoreSliderRow(
              subject = SubjectType.PHYSICS,
              score = uiState.scorePhysics,
              maxScore = maxScore,
              onScoreChange = { viewModel.updateSubjectScores(it, uiState.scoreChemistry, uiState.scoreMath) }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            ScoreSliderRow(
              subject = SubjectType.CHEMISTRY,
              score = uiState.scoreChemistry,
              maxScore = maxScore,
              onScoreChange = { viewModel.updateSubjectScores(uiState.scorePhysics, it, uiState.scoreMath) }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            ScoreSliderRow(
              subject = SubjectType.MATHEMATICS,
              score = uiState.scoreMath,
              maxScore = maxScore,
              onScoreChange = { viewModel.updateSubjectScores(uiState.scorePhysics, uiState.scoreChemistry, it) }
            )
          } else {
            // Question Marking Breakdown Mode (+4 / -1 / 0)
            QuestionBreakdownRow(
              subject = SubjectType.PHYSICS,
              breakdown = uiState.physicsBreakdown,
              onUpdate = { c, i, u -> viewModel.updateQuestionBreakdown(SubjectType.PHYSICS, c, i, u) }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            QuestionBreakdownRow(
              subject = SubjectType.CHEMISTRY,
              breakdown = uiState.chemistryBreakdown,
              onUpdate = { c, i, u -> viewModel.updateQuestionBreakdown(SubjectType.CHEMISTRY, c, i, u) }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

            QuestionBreakdownRow(
              subject = SubjectType.MATHEMATICS,
              breakdown = uiState.mathBreakdown,
              onUpdate = { c, i, u -> viewModel.updateQuestionBreakdown(SubjectType.MATHEMATICS, c, i, u) }
            )
          }
        }
      }
    }

    // 5. College & Branch Admission Probabilities
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "JoSAA College Admission Radar",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          listOf("ALL", "IIT", "NIT", "BITS").forEach { tag ->
            val isSelected = selectedCollegeFilter == tag
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant)
                .clickable { selectedCollegeFilter = tag }
                .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
              Text(
                text = tag,
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }
      }
    }

    item {
      EnhancedAdmissionRadarList(
        predictedAir = uiState.predictedAir,
        filter = selectedCollegeFilter
      )
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }
}

@Composable
fun ScoreSimulatorTabContent(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Giant Animated Jump Banner
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("simulator_jump_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, BrandEmerald.copy(alpha = 0.7f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = null,
                tint = BrandEmerald,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "SCORE IMPROVEMENT SIMULATOR",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.ExtraBold,
                color = BrandEmerald,
                letterSpacing = 0.8.sp
              )
            }

            if (uiState.scoreImprovement > 0) {
              IconButton(
                onClick = { viewModel.resetSimDeltas() },
                modifier = Modifier.size(28.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.RestartAlt,
                  contentDescription = "Reset Simulation",
                  tint = MaterialTheme.colorScheme.onSurfaceVariant,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Before & After Rank Visualizer
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Current State
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "AIR ${uiState.predictedAir}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "Current (${uiState.totalScore}M)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            // Leap Badge
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(BrandEmerald)
                .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                  text = "+${uiState.rankImprovement} AIR Leap 🚀",
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Black,
                  color = Color.Black
                )
                Text(
                  text = "+${uiState.scoreImprovement} Marks Gain",
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.Black.copy(alpha = 0.8f)
                )
              }
            }

            // Simulated State
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "AIR ${uiState.simPredictedAir}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                color = BrandEmerald
              )
              Text(
                text = "Simulated (${uiState.simTotalScore}M)",
                style = MaterialTheme.typography.labelSmall,
                color = BrandEmerald,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Unlocked College Alerts
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(12.dp)
          ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "🔓 Unlocked with +${uiState.scoreImprovement} Marks (${uiState.simTotalScore}/${uiState.maxPossibleScore}):",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = BrandAccentGold
              )
              Text(
                text = when {
                  uiState.simPredictedAir <= 500 -> "• IIT Bombay / IIT Delhi Computer Science & Engineering\n• Guaranteed Top 5 IIT Core & Circuit Branches\n• 100% Full Fee Waiver at top institutes"
                  uiState.simPredictedAir <= 2500 -> "• IIT Bombay Electrical Engg / IIT Kharagpur CSE\n• BITS Pilani Computer Science & Dual Degrees\n• IIT Madras Data Science & Artificial Intelligence"
                  uiState.simPredictedAir <= 6500 -> "• NIT Trichy / Warangal / Surathkal CSE\n• IIIT Hyderabad ECE & CS\n• IIT Roorkee / Guwahati Mechanical & Chemical"
                  uiState.simPredictedAir <= 15000 -> "• Top 10 NITs Electronics & Communication (ECE)\n• Top IIITs IT & Data Science\n• Clears JEE Advanced Cutoff easily!"
                  else -> "• Top NITs Electrical & Civil Engineering\n• JoSAA Counseling round 1 confirmation"
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
              )
            }
          }
        }
      }
    }

    // 2. Reverse Target Goal Presets
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Target-to-Action Reverse Calculator",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Pick Dream Goal 🎯",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(SampleData.targetPresets) { preset ->
            val isSelected = uiState.selectedTargetPresetId == preset.id
            OutlinedCard(
              modifier = Modifier
                .width(220.dp)
                .clickable { viewModel.applyTargetPreset(preset) },
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.outlinedCardColors(
                containerColor = if (isSelected) BrandPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
              ),
              border = BorderStroke(
                if (isSelected) 1.5.dp else 1.dp,
                if (isSelected) BrandPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
              )
            ) {
              Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(text = preset.iconEmoji, fontSize = 16.sp)
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = preset.title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
                Text(
                  text = "Target: ${preset.targetScoreMain}M • AIR <= ${preset.targetAir}",
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  color = BrandEmerald,
                  fontWeight = FontWeight.SemiBold
                )
                Text(
                  text = preset.collegeTarget,
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 10.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis
                )
              }
            }
          }
        }
      }
    }

    // 3. One-Tap "What-If" High-ROI Scenarios
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "One-Tap What-If Simulation Scenarios",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          SampleData.whatIfScenarios.forEach { scenario ->
            val isActive = uiState.activeWhatIfScenarioId == scenario.id
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.applyWhatIfScenario(scenario) },
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isActive) BrandEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
              ),
              border = BorderStroke(
                if (isActive) 1.5.dp else 1.dp,
                if (isActive) BrandEmerald else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
              )
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Box(
                  modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(BrandEmerald.copy(alpha = 0.15f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(text = scenario.iconEmoji, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = scenario.title,
                      style = MaterialTheme.typography.titleSmall,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.onSurface
                    )

                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(BrandEmerald)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                      Text(
                        text = "+${scenario.deltaPhysics + scenario.deltaChemistry + scenario.deltaMath}M",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                      )
                    }
                  }

                  Text(
                    text = scenario.description,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )

                  Text(
                    text = "⏱️ Est. study effort: ~${scenario.estimatedHoursNeeded} focused hours",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = BrandAccentGold
                  )
                }
              }
            }
          }
        }
      }
    }

    // 4. Fine-Tune Subject Delta Sliders
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(
            text = "Custom Subject Gains Slider (+Δ Marks)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Slide each subject to simulate incremental gains from chapter-wise mastery.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          DeltaSliderRow(
            subject = SubjectType.PHYSICS,
            delta = uiState.simDeltaPhysics,
            current = uiState.effectivePhysicsScore,
            maxScore = if (uiState.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100,
            onDeltaChange = { viewModel.updateSimDeltas(it, uiState.simDeltaChemistry, uiState.simDeltaMath) }
          )

          DeltaSliderRow(
            subject = SubjectType.CHEMISTRY,
            delta = uiState.simDeltaChemistry,
            current = uiState.effectiveChemistryScore,
            maxScore = if (uiState.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100,
            onDeltaChange = { viewModel.updateSimDeltas(uiState.simDeltaPhysics, it, uiState.simDeltaMath) }
          )

          DeltaSliderRow(
            subject = SubjectType.MATHEMATICS,
            delta = uiState.simDeltaMath,
            current = uiState.effectiveMathScore,
            maxScore = if (uiState.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100,
            onDeltaChange = { viewModel.updateSimDeltas(uiState.simDeltaPhysics, uiState.simDeltaChemistry, it) }
          )
        }
      }
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }
}

@Composable
fun ScoreSliderRow(
  subject: SubjectType,
  score: Int,
  maxScore: Int,
  onScoreChange: (Int) -> Unit
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = subject.displayName,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = subject.color
      )
      Text(
        text = "$score / $maxScore",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
    Slider(
      value = score.toFloat(),
      onValueChange = { onScoreChange(it.toInt()) },
      valueRange = 0f..maxScore.toFloat(),
      colors = SliderDefaults.colors(
        thumbColor = subject.color,
        activeTrackColor = subject.color,
        inactiveTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
      )
    )
  }
}

@Composable
fun QuestionBreakdownRow(
  subject: SubjectType,
  breakdown: SubjectQuestionBreakdown,
  onUpdate: (correct: Int, incorrect: Int, unattempted: Int) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(subject.color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = subject.displayName,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = subject.color
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "Net: ${breakdown.calculatedMarks}M",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "${String.format(Locale.US, "%.0f", breakdown.accuracyPercentage)}% Acc",
          style = MaterialTheme.typography.labelSmall,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = if (breakdown.accuracyPercentage >= 80) BrandEmerald else BrandAccentGold
        )
      }
    }

    // Question Steppers: Correct (+4), Incorrect (-1), Unattempted (0)
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // Correct Counter
      QuestionCounterPill(
        label = "Correct (+4)",
        count = breakdown.correctCount,
        color = BrandEmerald,
        onIncrement = { onUpdate(breakdown.correctCount + 1, breakdown.incorrectCount, breakdown.unattemptedCount) },
        onDecrement = { onUpdate((breakdown.correctCount - 1).coerceAtLeast(0), breakdown.incorrectCount, breakdown.unattemptedCount) },
        modifier = Modifier.weight(1f)
      )

      // Incorrect Counter
      QuestionCounterPill(
        label = "Wrong (-1)",
        count = breakdown.incorrectCount,
        color = BrandRose,
        onIncrement = { onUpdate(breakdown.correctCount, breakdown.incorrectCount + 1, breakdown.unattemptedCount) },
        onDecrement = { onUpdate(breakdown.correctCount, (breakdown.incorrectCount - 1).coerceAtLeast(0), breakdown.unattemptedCount) },
        modifier = Modifier.weight(1f)
      )

      // Unattempted Counter
      QuestionCounterPill(
        label = "Skip (0)",
        count = breakdown.unattemptedCount,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        onIncrement = { onUpdate(breakdown.correctCount, breakdown.incorrectCount, breakdown.unattemptedCount + 1) },
        onDecrement = { onUpdate(breakdown.correctCount, breakdown.incorrectCount, (breakdown.unattemptedCount - 1).coerceAtLeast(0)) },
        modifier = Modifier.weight(1f)
      )
    }
  }
}

@Composable
fun QuestionCounterPill(
  label: String,
  count: Int,
  color: Color,
  onIncrement: () -> Unit,
  onDecrement: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(10.dp))
      .background(color.copy(alpha = 0.1f))
      .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
      .padding(6.dp)
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontSize = 9.sp,
        fontWeight = FontWeight.Bold,
        color = color
      )
      Spacer(modifier = Modifier.height(2.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.2f))
            .clickable(onClick = onDecrement),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Remove, contentDescription = "Decrement", tint = color, modifier = Modifier.size(12.dp))
        }

        Text(
          text = "$count",
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = MaterialTheme.colorScheme.onSurface
        )

        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.2f))
            .clickable(onClick = onIncrement),
          contentAlignment = Alignment.Center
        ) {
          Icon(Icons.Default.Add, contentDescription = "Increment", tint = color, modifier = Modifier.size(12.dp))
        }
      }
    }
  }
}

@Composable
fun DeltaSliderRow(
  subject: SubjectType,
  delta: Int,
  current: Int,
  maxScore: Int,
  onDeltaChange: (Int) -> Unit
) {
  val maxPossibleDelta = (maxScore - current).coerceAtLeast(0)

  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "${subject.displayName} (Base: $current)",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = subject.color
      )
      Text(
        text = "+$delta Marks -> ${(current + delta)}M",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
        color = BrandEmerald
      )
    }
    Slider(
      value = delta.toFloat(),
      onValueChange = { onDeltaChange(it.toInt()) },
      valueRange = 0f..maxPossibleDelta.toFloat().coerceAtLeast(1f),
      colors = SliderDefaults.colors(
        thumbColor = BrandEmerald,
        activeTrackColor = BrandEmerald,
        inactiveTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
      )
    )
  }
}

@Composable
fun EnhancedAdmissionRadarList(
  predictedAir: Int,
  filter: String
) {
  val allColleges = listOf(
    CollegeRadarItem("IIT Bombay", "Computer Science & Engg", "IIT", 65, 95, 20),
    CollegeRadarItem("IIT Bombay", "Electrical Engineering", "IIT", 400, 92, 45),
    CollegeRadarItem("IIT Delhi", "Mathematics & Computing", "IIT", 650, 90, 50),
    CollegeRadarItem("IIT Madras", "Mechanical Engineering", "IIT", 2500, 94, 65),
    CollegeRadarItem("IIT Kanpur", "Aerospace Engineering", "IIT", 3800, 94, 68),
    CollegeRadarItem("BITS Pilani", "Computer Science", "BITS", 4500, 96, 75),
    CollegeRadarItem("NIT Trichy", "Computer Science", "NIT", 5000, 98, 80),
    CollegeRadarItem("IIIT Hyderabad", "Electronics & Comm (ECE)", "IIIT", 5800, 95, 78),
    CollegeRadarItem("NIT Warangal", "Electrical & Electronics", "NIT", 9500, 96, 85),
    CollegeRadarItem("NIT Surathkal", "Mechanical Engineering", "NIT", 14000, 98, 90),
    CollegeRadarItem("IIIT Delhi", "Computer Science & AI", "IIIT", 16000, 98, 88),
    CollegeRadarItem("NIT Rourkela", "Chemical Engineering", "NIT", 24000, 99, 92)
  )

  val filtered = if (filter == "ALL") allColleges else allColleges.filter { it.type == filter }

  Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    filtered.forEach { item ->
      val chance = when {
        predictedAir <= item.closingAir * 0.7 -> 96
        predictedAir <= item.closingAir -> 82
        predictedAir <= item.closingAir * 1.3 -> 52
        else -> 18
      }
      val statusColor = if (chance >= 80) BrandEmerald else if (chance >= 50) BrandAccentGold else BrandRose
      val statusText = if (chance >= 80) "Safe Bet" else if (chance >= 50) "Target / Moderate" else "Reach"

      OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.35f))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(MaterialTheme.colorScheme.surfaceVariant)
                  .padding(horizontal = 5.dp, vertical = 1.dp)
              ) {
                Text(
                  text = item.type,
                  style = MaterialTheme.typography.labelSmall,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold,
                  color = BrandPrimary
                )
              }
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = item.collegeName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            Text(
              text = item.branchName,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "Last Year JoSAA Cutoff: AIR ~${item.closingAir}",
              style = MaterialTheme.typography.labelSmall,
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.outline
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(statusColor.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "$chance% • $statusText",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = statusColor
            )
          }
        }
      }
    }
  }
}

data class CollegeRadarItem(
  val collegeName: String,
  val branchName: String,
  val type: String,
  val closingAir: Int,
  val maxChance: Int,
  val minChance: Int
)
