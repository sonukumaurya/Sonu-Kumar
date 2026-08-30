package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AnalyticsMetricView
import com.example.data.AnalyticsTimeRange
import com.example.data.PracticeTestScorePoint
import com.example.data.SubjectType
import com.example.data.SyllabusProgressPoint
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnalyticsScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  var showAddTestModal by remember { mutableStateOf(false) }
  var selectedTabSection by remember { mutableIntStateOf(0) } // 0 = All Visuals, 1 = Syllabus Completion, 2 = Test Scores

  // Filter test points based on TimeRange and ExamType
  val filteredTests = remember(
    uiState.practiceTestScores,
    uiState.selectedAnalyticsTimeRange,
    uiState.selectedExamTypeFilter
  ) {
    var list = uiState.practiceTestScores
    if (uiState.selectedExamTypeFilter != "ALL") {
      list = list.filter { it.examType == uiState.selectedExamTypeFilter }
    }
    when (uiState.selectedAnalyticsTimeRange) {
      AnalyticsTimeRange.LAST_4_WEEKS -> list.takeLast(4)
      AnalyticsTimeRange.LAST_3_MONTHS -> list.takeLast(8)
      AnalyticsTimeRange.ALL_TIME -> list
    }
  }

  // Selected scrubbed test point
  val activeTest = remember(filteredTests, uiState.selectedScrubbedTestId) {
    filteredTests.find { it.id == uiState.selectedScrubbedTestId } ?: filteredTests.lastOrNull()
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("analytics_screen"),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    // 1. Hero Summary Header
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("analytics_hero_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, BrandPrimary.copy(alpha = 0.5f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(BrandPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Timeline,
                  contentDescription = null,
                  tint = BrandPrimary,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "Visual Progress Dashboard",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.ExtraBold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "Recharts-Powered Syllabus & Test Analytics",
                  style = MaterialTheme.typography.bodySmall,
                  color = BrandPrimary,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }

            Button(
              onClick = { showAddTestModal = true },
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = BrandPrimary,
                contentColor = Color.Black
              ),
              modifier = Modifier.testTag("log_test_score_btn")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Log Test", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }

          // Key Headline Metrics Grid
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            val latestTest = uiState.practiceTestScores.lastOrNull()
            val latestSyllabus = uiState.syllabusProgressHistory.lastOrNull()

            MetricBadgeItem(
              label = "Syllabus Done",
              value = "${latestSyllabus?.overallPercent?.toInt() ?: 84}%",
              sub = "+12% this month",
              color = BrandEmerald,
              modifier = Modifier.weight(1f)
            )
            MetricBadgeItem(
              label = "Latest Mock",
              value = "${latestTest?.totalScore ?: 242}/300",
              sub = "${latestTest?.percentile ?: 99.35}%ile",
              color = BrandPrimary,
              modifier = Modifier.weight(1f)
            )
            MetricBadgeItem(
              label = "Est. AIR",
              value = "#${latestTest?.projectedAir ?: 6840}",
              sub = "Top 0.65% CRL",
              color = BrandAccentGold,
              modifier = Modifier.weight(1f)
            )
          }

          // Section Selector Tabs
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            listOf("📊 Overview", "📚 Syllabus Velocity", "📈 Test Scores").forEachIndexed { idx, title ->
              val isSelected = selectedTabSection == idx
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isSelected) BrandPrimary else Color.Transparent)
                  .clickable { selectedTabSection = idx }
                  .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = title,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // 2. VISUAL CHART 1: SYLLABUS COMPLETION OVER TIME (Recharts-style Area & Multi-Line Chart)
    if (selectedTabSection == 0 || selectedTabSection == 1) {
      item {
        SyllabusCompletionChartCard(
          history = uiState.syllabusProgressHistory
        )
      }
    }

    // 3. VISUAL CHART 2: PRACTICE TEST SCORES & PERCENTILE OVER TIME (Recharts-style Area Chart with Tooltip Inspection)
    if (selectedTabSection == 0 || selectedTabSection == 2) {
      item {
        PracticeTestScoresChartCard(
          filteredTests = filteredTests,
          activeTest = activeTest,
          selectedMetric = uiState.selectedAnalyticsMetric,
          selectedTimeRange = uiState.selectedAnalyticsTimeRange,
          selectedExamFilter = uiState.selectedExamTypeFilter,
          onSelectMetric = { viewModel.setAnalyticsMetric(it) },
          onSelectTimeRange = { viewModel.setAnalyticsTimeRange(it) },
          onSelectExamFilter = { viewModel.setExamTypeFilter(it) },
          onScrubTest = { viewModel.selectScrubbedTest(it) }
        )
      }
    }

    // 4. SUBJECT-WISE ACCURACY & SPEED MATRIX
    item {
      SubjectAccuracyMatrixCard(
        chapters = uiState.chapters,
        latestScore = uiState.practiceTestScores.lastOrNull()
      )
    }

    // 5. RECENT MOCK TEST LOGS HISTORY
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.QueryStats, contentDescription = null, tint = BrandSecondary, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Mock Test Timeline Logs",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
            Text(
              text = "${uiState.practiceTestScores.size} Tests Logged",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          uiState.practiceTestScores.takeLast(5).reversed().forEach { test ->
            TestLogRowItem(
              test = test,
              isSelected = test.id == uiState.selectedScrubbedTestId,
              onClick = { viewModel.selectScrubbedTest(test.id) }
            )
          }
        }
      }
    }

    item { Spacer(modifier = Modifier.height(32.dp)) }
  }

  // Dialog to Log New Test Score
  if (showAddTestModal) {
    LogTestScoreDialog(
      onDismiss = { showAddTestModal = false },
      onSubmit = { name, examType, phy, chem, math, acc ->
        viewModel.logNewPracticeTest(name, examType, phy, chem, math, acc)
        showAddTestModal = false
      }
    )
  }
}

// -------------------------------------------------------------
// RECHARTS-STYLE SYLLABUS COMPLETION OVER TIME CHART
// -------------------------------------------------------------
@Composable
fun SyllabusCompletionChartCard(
  history: List<SyllabusProgressPoint>,
  modifier: Modifier = Modifier
) {
  var selectedSubjectFilter by remember { mutableStateOf<SubjectType?>(null) } // null = All
  var hoveredIndex by remember { mutableIntStateOf(history.lastIndex) }

  val activePoint = history.getOrNull(hoveredIndex) ?: history.last()

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("syllabus_progress_chart_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.MenuBook, contentDescription = null, tint = BrandEmerald, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Syllabus Mastery Trajectory",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Text(
            text = "Weekly completion pace vs target baseline",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Live inspection pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(BrandEmerald.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "${activePoint.weekLabel}: ${activePoint.overallPercent.toInt()}% Done",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandEmerald
          )
        }
      }

      // Legend Filters (Physics, Chem, Math, Overall, Target)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        LegendChip(
          title = "Overall",
          color = BrandEmerald,
          isSelected = selectedSubjectFilter == null,
          onClick = { selectedSubjectFilter = null }
        )
        LegendChip(
          title = "Physics",
          color = PhysicsColor,
          isSelected = selectedSubjectFilter == SubjectType.PHYSICS,
          onClick = { selectedSubjectFilter = SubjectType.PHYSICS }
        )
        LegendChip(
          title = "Chem",
          color = ChemistryColor,
          isSelected = selectedSubjectFilter == SubjectType.CHEMISTRY,
          onClick = { selectedSubjectFilter = SubjectType.CHEMISTRY }
        )
        LegendChip(
          title = "Math",
          color = MathColor,
          isSelected = selectedSubjectFilter == SubjectType.MATHEMATICS,
          onClick = { selectedSubjectFilter = SubjectType.MATHEMATICS }
        )
      }

      // Recharts Interactive Canvas Chart
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
          .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
      ) {
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
              detectTapGestures { offset ->
                val count = history.size
                if (count > 1) {
                  val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                  val index = (fraction * (count - 1)).roundToInt().coerceIn(0, count - 1)
                  hoveredIndex = index
                }
              }
            }
        ) {
          val w = size.width
          val h = size.height
          val paddingBottom = 24.dp.toPx()
          val chartH = h - paddingBottom
          val count = history.size

          if (count < 2) return@Canvas

          // Draw Gridlines (25%, 50%, 75%, 100%)
          val gridLines = listOf(0.25f, 0.50f, 0.75f, 1.0f)
          gridLines.forEach { frac ->
            val y = chartH - (frac * chartH)
            drawLine(
              color = Color.Gray.copy(alpha = 0.15f),
              start = Offset(0f, y),
              end = Offset(w, y),
              strokeWidth = 1.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
            )
          }

          // Target Baseline Line (Dashed)
          val targetPath = Path()
          history.forEachIndexed { i, pt ->
            val x = (i.toFloat() / (count - 1)) * w
            val y = chartH - ((pt.targetPacePercent / 100f) * chartH)
            if (i == 0) targetPath.moveTo(x, y) else targetPath.lineTo(x, y)
          }
          drawPath(
            path = targetPath,
            color = Color.Gray.copy(alpha = 0.45f),
            style = Stroke(
              width = 1.5.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
            )
          )

          // Area Fill & Smooth Curve for Subject or Overall
          val activePoints: List<Float> = history.map { pt ->
            when (selectedSubjectFilter) {
              SubjectType.PHYSICS -> pt.physicsPercent
              SubjectType.CHEMISTRY -> pt.chemistryPercent
              SubjectType.MATHEMATICS -> pt.mathPercent
              null -> pt.overallPercent
            }
          }

          val curveColor = when (selectedSubjectFilter) {
            SubjectType.PHYSICS -> PhysicsColor
            SubjectType.CHEMISTRY -> ChemistryColor
            SubjectType.MATHEMATICS -> MathColor
            null -> BrandEmerald
          }

          // Build Curve Path & Area Path
          val strokePath = Path()
          val areaPath = Path()

          areaPath.moveTo(0f, chartH)

          val coordinates = activePoints.mapIndexed { i, value ->
            val x = (i.toFloat() / (count - 1)) * w
            val y = chartH - ((value / 100f).coerceIn(0f, 1f) * chartH)
            Offset(x, y)
          }

          strokePath.moveTo(coordinates.first().x, coordinates.first().y)
          areaPath.lineTo(coordinates.first().x, coordinates.first().y)

          for (i in 0 until coordinates.size - 1) {
            val p0 = coordinates[i]
            val p1 = coordinates[i + 1]
            val controlX1 = (p0.x + p1.x) / 2
            val controlY1 = p0.y
            val controlX2 = (p0.x + p1.x) / 2
            val controlY2 = p1.y
            strokePath.cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
            areaPath.cubicTo(controlX1, controlY1, controlX2, controlY2, p1.x, p1.y)
          }

          areaPath.lineTo(w, chartH)
          areaPath.close()

          // Draw Area Gradient Fill (Recharts signature look)
          drawPath(
            path = areaPath,
            brush = Brush.verticalGradient(
              colors = listOf(
                curveColor.copy(alpha = 0.38f),
                curveColor.copy(alpha = 0.08f),
                Color.Transparent
              ),
              startY = 0f,
              endY = chartH
            )
          )

          // Draw Stroke Curve
          drawPath(
            path = strokePath,
            color = curveColor,
            style = Stroke(
              width = 3.dp.toPx(),
              cap = StrokeCap.Round,
              join = StrokeJoin.Round
            )
          )

          // Draw Active Hovered Point & Vertical Scrubber Line
          val hoveredPoint = coordinates[hoveredIndex]
          drawLine(
            color = curveColor.copy(alpha = 0.6f),
            start = Offset(hoveredPoint.x, 0f),
            end = Offset(hoveredPoint.x, chartH),
            strokeWidth = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
          )

          // Dot Circle
          drawCircle(
            color = Color.Black,
            radius = 6.dp.toPx(),
            center = hoveredPoint
          )
          drawCircle(
            color = curveColor,
            radius = 4.5.dp.toPx(),
            center = hoveredPoint
          )
          drawCircle(
            color = Color.White,
            radius = 2.dp.toPx(),
            center = hoveredPoint
          )
        }
      }

      // Inspection Card for the active selected point
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "📅 ${activePoint.weekLabel} Snapshot",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "${activePoint.chaptersMasteredCumulative} Chapters Mastered • ${activePoint.pyqsSolvedCumulative} PYQs Solved",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("PHY: ${activePoint.physicsPercent.toInt()}%", fontSize = 10.sp, color = PhysicsColor, fontWeight = FontWeight.Bold)
          Text("CHEM: ${activePoint.chemistryPercent.toInt()}%", fontSize = 10.sp, color = ChemistryColor, fontWeight = FontWeight.Bold)
          Text("MATH: ${activePoint.mathPercent.toInt()}%", fontSize = 10.sp, color = MathColor, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

// -------------------------------------------------------------
// RECHARTS-STYLE PRACTICE TEST SCORES OVER TIME CHART
// -------------------------------------------------------------
@Composable
fun PracticeTestScoresChartCard(
  filteredTests: List<PracticeTestScorePoint>,
  activeTest: PracticeTestScorePoint?,
  selectedMetric: AnalyticsMetricView,
  selectedTimeRange: AnalyticsTimeRange,
  selectedExamFilter: String,
  onSelectMetric: (AnalyticsMetricView) -> Unit,
  onSelectTimeRange: (AnalyticsTimeRange) -> Unit,
  onSelectExamFilter: (String) -> Unit,
  onScrubTest: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("practice_test_scores_chart_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // Header with Filter Controls
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ShowChart, contentDescription = null, tint = BrandPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Practice Test Score Progression",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
          Text(
            text = "Mock test trajectory & AIR jump curve",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        // Exam Type Selector (All, Main, Adv)
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(2.dp)
        ) {
          listOf("ALL", "JEE Main", "JEE Advanced").forEach { filter ->
            val isSelected = selectedExamFilter == filter
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(if (isSelected) BrandPrimary else Color.Transparent)
                .clickable { onSelectExamFilter(filter) }
                .padding(horizontal = 6.dp, vertical = 4.dp)
            ) {
              Text(
                text = if (filter == "JEE Main") "Main" else if (filter == "JEE Advanced") "Adv" else "All",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
      }

      // Metric Toggles (Score, Percentile, Subject Split, Accuracy)
      LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(AnalyticsMetricView.entries) { metric ->
          val isSelected = selectedMetric == metric
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(if (isSelected) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant)
              .clickable { onSelectMetric(metric) }
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(
              text = metric.label,
              style = MaterialTheme.typography.labelSmall,
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }

      // Recharts Canvas Multi-Line & Area Chart
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(210.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f))
          .padding(top = 16.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
      ) {
        Canvas(
          modifier = Modifier
            .fillMaxSize()
            .pointerInput(filteredTests) {
              detectTapGestures { offset ->
                if (filteredTests.isNotEmpty()) {
                  val count = filteredTests.size
                  val fraction = (offset.x / size.width).coerceIn(0f, 1f)
                  val index = if (count == 1) 0 else (fraction * (count - 1)).roundToInt().coerceIn(0, count - 1)
                  onScrubTest(filteredTests[index].id)
                }
              }
            }
        ) {
          val w = size.width
          val h = size.height
          val paddingBottom = 20.dp.toPx()
          val chartH = h - paddingBottom
          val count = filteredTests.size

          if (count < 2) return@Canvas

          // Draw Y-Axis Gridlines
          val gridFractions = listOf(0.25f, 0.50f, 0.75f, 1.0f)
          gridFractions.forEach { frac ->
            val y = chartH - (frac * chartH)
            drawLine(
              color = Color.Gray.copy(alpha = 0.15f),
              start = Offset(0f, y),
              end = Offset(w, y),
              strokeWidth = 1.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
            )
          }

          // Calculate Normalized Y Coordinates based on selected metric
          when (selectedMetric) {
            AnalyticsMetricView.TOTAL_SCORE -> {
              val coordinates = filteredTests.mapIndexed { i, test ->
                val x = (i.toFloat() / (count - 1)) * w
                val normScore = (test.totalScore.toFloat() / test.maxMarks.toFloat()).coerceIn(0f, 1f)
                val y = chartH - (normScore * chartH)
                Offset(x, y)
              }

              // Area and Stroke
              val areaPath = Path()
              val strokePath = Path()
              areaPath.moveTo(0f, chartH)
              areaPath.lineTo(coordinates.first().x, coordinates.first().y)
              strokePath.moveTo(coordinates.first().x, coordinates.first().y)

              for (i in 0 until coordinates.size - 1) {
                val p0 = coordinates[i]
                val p1 = coordinates[i + 1]
                val cx1 = (p0.x + p1.x) / 2
                val cy1 = p0.y
                val cx2 = (p0.x + p1.x) / 2
                val cy2 = p1.y
                strokePath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
                areaPath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
              }

              areaPath.lineTo(w, chartH)
              areaPath.close()

              // Area fill
              drawPath(
                path = areaPath,
                brush = Brush.verticalGradient(
                  colors = listOf(BrandPrimary.copy(alpha = 0.4f), BrandPrimary.copy(alpha = 0.05f), Color.Transparent),
                  startY = 0f,
                  endY = chartH
                )
              )

              // Line stroke
              drawPath(
                path = strokePath,
                color = BrandPrimary,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
              )

              // Draw Data Points
              coordinates.forEachIndexed { i, pt ->
                val isSelected = filteredTests[i].id == activeTest?.id
                drawCircle(color = Color.Black, radius = if (isSelected) 7.dp.toPx() else 4.dp.toPx(), center = pt)
                drawCircle(color = if (isSelected) BrandAccentGold else BrandPrimary, radius = if (isSelected) 5.dp.toPx() else 3.dp.toPx(), center = pt)
              }
            }

            AnalyticsMetricView.PERCENTILE -> {
              val minP = 90.0
              val maxP = 100.0
              val coordinates = filteredTests.mapIndexed { i, test ->
                val x = (i.toFloat() / (count - 1)) * w
                val normP = ((test.percentile - minP) / (maxP - minP)).toFloat().coerceIn(0f, 1f)
                val y = chartH - (normP * chartH)
                Offset(x, y)
              }

              val areaPath = Path()
              val strokePath = Path()
              areaPath.moveTo(0f, chartH)
              areaPath.lineTo(coordinates.first().x, coordinates.first().y)
              strokePath.moveTo(coordinates.first().x, coordinates.first().y)

              for (i in 0 until coordinates.size - 1) {
                val p0 = coordinates[i]
                val p1 = coordinates[i + 1]
                val cx1 = (p0.x + p1.x) / 2
                val cy1 = p0.y
                val cx2 = (p0.x + p1.x) / 2
                val cy2 = p1.y
                strokePath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
                areaPath.cubicTo(cx1, cy1, cx2, cy2, p1.x, p1.y)
              }
              areaPath.lineTo(w, chartH)
              areaPath.close()

              drawPath(
                path = areaPath,
                brush = Brush.verticalGradient(
                  colors = listOf(BrandAccentGold.copy(alpha = 0.35f), Color.Transparent),
                  startY = 0f,
                  endY = chartH
                )
              )
              drawPath(
                path = strokePath,
                color = BrandAccentGold,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
              )

              coordinates.forEachIndexed { i, pt ->
                val isSelected = filteredTests[i].id == activeTest?.id
                drawCircle(color = Color.Black, radius = if (isSelected) 7.dp.toPx() else 4.dp.toPx(), center = pt)
                drawCircle(color = BrandAccentGold, radius = if (isSelected) 5.dp.toPx() else 3.dp.toPx(), center = pt)
              }
            }

            AnalyticsMetricView.SUBJECT_SPLIT -> {
              // 3 Distinct Lines for Phy, Chem, Math
              val maxSubM = 100f

              val phyCoords = filteredTests.mapIndexed { i, t -> Offset((i.toFloat() / (count - 1)) * w, chartH - (t.physicsMarks / maxSubM) * chartH) }
              val chemCoords = filteredTests.mapIndexed { i, t -> Offset((i.toFloat() / (count - 1)) * w, chartH - (t.chemistryMarks / maxSubM) * chartH) }
              val mathCoords = filteredTests.mapIndexed { i, t -> Offset((i.toFloat() / (count - 1)) * w, chartH - (t.mathMarks / maxSubM) * chartH) }

              listOf(
                Triple(phyCoords, PhysicsColor, "Phy"),
                Triple(chemCoords, ChemistryColor, "Chem"),
                Triple(mathCoords, MathColor, "Math")
              ).forEach { (coords, color, _) ->
                val path = Path()
                path.moveTo(coords.first().x, coords.first().y)
                for (i in 0 until coords.size - 1) {
                  val p0 = coords[i]
                  val p1 = coords[i + 1]
                  path.cubicTo((p0.x + p1.x)/2, p0.y, (p0.x + p1.x)/2, p1.y, p1.x, p1.y)
                }
                drawPath(path = path, color = color, style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round))
                coords.forEach { pt ->
                  drawCircle(color = color, radius = 3.5.dp.toPx(), center = pt)
                }
              }
            }

            AnalyticsMetricView.ACCURACY -> {
              val coordinates = filteredTests.mapIndexed { i, test ->
                val x = (i.toFloat() / (count - 1)) * w
                val normAcc = (test.accuracyPercent.toFloat() / 100f).coerceIn(0f, 1f)
                val y = chartH - (normAcc * chartH)
                Offset(x, y)
              }

              val path = Path()
              path.moveTo(coordinates.first().x, coordinates.first().y)
              for (i in 0 until coordinates.size - 1) {
                val p0 = coordinates[i]
                val p1 = coordinates[i + 1]
                path.cubicTo((p0.x + p1.x)/2, p0.y, (p0.x + p1.x)/2, p1.y, p1.x, p1.y)
              }
              drawPath(path = path, color = BrandEmerald, style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round))
              coordinates.forEachIndexed { i, pt ->
                drawCircle(color = BrandEmerald, radius = 4.dp.toPx(), center = pt)
              }
            }
          }
        }
      }

      // Active Hovered/Scrubbed Test Detail Card
      if (activeTest != null) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, BrandPrimary.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
            .padding(12.dp)
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = activeTest.testName,
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "${activeTest.dateLabel} • ${activeTest.examType} (Max ${activeTest.maxMarks}M)",
                  style = MaterialTheme.typography.bodySmall,
                  fontSize = 11.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "${activeTest.totalScore}/${activeTest.maxMarks} M",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.ExtraBold,
                  color = BrandPrimary
                )
                Text(
                  text = "${activeTest.percentile}%ile • AIR ~${activeTest.projectedAir}",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = BrandAccentGold
                )
              }
            }

            // Subject Marks Split Pills
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              SubjectScorePill(
                name = "Physics",
                score = activeTest.physicsMarks,
                color = PhysicsColor,
                modifier = Modifier.weight(1f)
              )
              SubjectScorePill(
                name = "Chemistry",
                score = activeTest.chemistryMarks,
                color = ChemistryColor,
                modifier = Modifier.weight(1f)
              )
              SubjectScorePill(
                name = "Maths",
                score = activeTest.mathMarks,
                color = MathColor,
                modifier = Modifier.weight(1f)
              )
              SubjectScorePill(
                name = "Accuracy",
                score = "${activeTest.accuracyPercent.toInt()}%",
                color = BrandEmerald,
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }
    }
  }
}

// -------------------------------------------------------------
// SUBJECT ACCURACY & PYQ MATRIX
// -------------------------------------------------------------
@Composable
fun SubjectAccuracyMatrixCard(
  chapters: List<com.example.data.Chapter>,
  latestScore: PracticeTestScorePoint?,
  modifier: Modifier = Modifier
) {
  val phyChapters = chapters.filter { it.subject == SubjectType.PHYSICS }
  val chemChapters = chapters.filter { it.subject == SubjectType.CHEMISTRY }
  val mathChapters = chapters.filter { it.subject == SubjectType.MATHEMATICS }

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.Speed, contentDescription = null, tint = BrandSecondary, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Subject Mastery & Question Bank",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
        Text(
          text = "Real-time Metrics",
          style = MaterialTheme.typography.labelSmall,
          color = BrandEmerald,
          fontWeight = FontWeight.Bold
        )
      }

      SubjectMasteryBar(
        subject = SubjectType.PHYSICS,
        score = latestScore?.physicsMarks ?: 86,
        maxScore = 100,
        chapters = phyChapters
      )
      SubjectMasteryBar(
        subject = SubjectType.CHEMISTRY,
        score = latestScore?.chemistryMarks ?: 86,
        maxScore = 100,
        chapters = chemChapters
      )
      SubjectMasteryBar(
        subject = SubjectType.MATHEMATICS,
        score = latestScore?.mathMarks ?: 70,
        maxScore = 100,
        chapters = mathChapters
      )
    }
  }
}

@Composable
fun SubjectMasteryBar(
  subject: SubjectType,
  score: Int,
  maxScore: Int,
  chapters: List<com.example.data.Chapter>
) {
  val totalPyqs = chapters.sumOf { it.totalPyqs }
  val solvedPyqs = chapters.sumOf { it.pyqsSolved }
  val pyqFraction = if (totalPyqs > 0) solvedPyqs.toFloat() / totalPyqs.toFloat() else 0f
  val masteredCount = chapters.count { it.status == com.example.data.ChapterStatus.MASTERED }

  Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(subject.color))
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = subject.displayName, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
      }
      Text(
        text = "Score: $score/$maxScore • PYQs: $solvedPyqs/$totalPyqs (${(pyqFraction*100).toInt()}%)",
        style = MaterialTheme.typography.labelSmall,
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    LinearProgressIndicator(
      progress = { pyqFraction },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = subject.color,
      trackColor = subject.color.copy(alpha = 0.2f),
    )
  }
}

// -------------------------------------------------------------
// HELPER UI PIECES
// -------------------------------------------------------------
@Composable
fun MetricBadgeItem(
  label: String,
  value: String,
  sub: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(color.copy(alpha = 0.12f))
      .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
      .padding(10.dp)
  ) {
    Column {
      Text(text = label, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = color)
      Text(text = sub, style = MaterialTheme.typography.bodySmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurface)
    }
  }
}

@Composable
fun LegendChip(
  title: String,
  color: Color,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(if (isSelected) color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)
      .border(1.dp, if (isSelected) color else Color.Transparent, RoundedCornerShape(8.dp))
      .clickable { onClick() }
      .padding(horizontal = 8.dp, vertical = 4.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(color))
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall,
      fontSize = 10.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) color else MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
fun SubjectScorePill(
  name: String,
  score: Any,
  color: Color,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(color.copy(alpha = 0.12f))
      .padding(vertical = 4.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(text = name, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(text = score.toString(), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
    }
  }
}

@Composable
fun TestLogRowItem(
  test: PracticeTestScorePoint,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  OutlinedCard(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() },
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.outlinedCardColors(
      containerColor = if (isSelected) BrandPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    ),
    border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, if (isSelected) BrandPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = test.testName,
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "${test.dateLabel} • ${test.examType} • P: ${test.physicsMarks} | C: ${test.chemistryMarks} | M: ${test.mathMarks}",
          style = MaterialTheme.typography.bodySmall,
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "${test.totalScore}/${test.maxMarks}",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.ExtraBold,
          color = BrandPrimary
        )
        Text(
          text = "${test.percentile}%ile",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.SemiBold,
          color = BrandEmerald
        )
      }
    }
  }
}

// -------------------------------------------------------------
// DIALOG: LOG NEW TEST SCORE
// -------------------------------------------------------------
@Composable
fun LogTestScoreDialog(
  onDismiss: () -> Unit,
  onSubmit: (name: String, examType: String, phy: Int, chem: Int, math: Int, acc: Double) -> Unit
) {
  var testName by remember { mutableStateOf("JEE Main Full Mock #${(15..20).random()}") }
  var examType by remember { mutableStateOf("JEE Main") }
  var phyScore by remember { mutableStateOf(80f) }
  var chemScore by remember { mutableStateOf(84f) }
  var mathScore by remember { mutableStateOf(72f) }
  var accuracy by remember { mutableStateOf(88f) }

  val maxM = if (examType == "JEE Advanced") 120f else 100f
  val totalScore = (phyScore + chemScore + mathScore).toInt()
  val totalMaxMarks = if (examType == "JEE Advanced") 360 else 300

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.TrendingUp, contentDescription = null, tint = BrandPrimary)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Log Practice Test Result", fontWeight = FontWeight.Bold)
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedTextField(
          value = testName,
          onValueChange = { testName = it },
          label = { Text("Test / Mock Name") },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(10.dp)
        )

        // Exam Type Toggle
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf("JEE Main", "JEE Advanced").forEach { t ->
            val isSelected = examType == t
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant)
                .clickable { examType = t }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = t,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp
              )
            }
          }
        }

        // Total Score Pill
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(BrandPrimary.copy(alpha = 0.15f))
            .padding(10.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Total Score: $totalScore / $totalMaxMarks M",
            fontWeight = FontWeight.ExtraBold,
            color = BrandPrimary,
            fontSize = 15.sp
          )
        }

        // Sliders for P, C, M
        Column {
          Text("Physics Marks: ${phyScore.toInt()}", fontSize = 11.sp, color = PhysicsColor, fontWeight = FontWeight.Bold)
          Slider(
            value = phyScore,
            onValueChange = { phyScore = it },
            valueRange = 0f..maxM,
            colors = SliderDefaults.colors(thumbColor = PhysicsColor, activeTrackColor = PhysicsColor)
          )
        }

        Column {
          Text("Chemistry Marks: ${chemScore.toInt()}", fontSize = 11.sp, color = ChemistryColor, fontWeight = FontWeight.Bold)
          Slider(
            value = chemScore,
            onValueChange = { chemScore = it },
            valueRange = 0f..maxM,
            colors = SliderDefaults.colors(thumbColor = ChemistryColor, activeTrackColor = ChemistryColor)
          )
        }

        Column {
          Text("Mathematics Marks: ${mathScore.toInt()}", fontSize = 11.sp, color = MathColor, fontWeight = FontWeight.Bold)
          Slider(
            value = mathScore,
            onValueChange = { mathScore = it },
            valueRange = 0f..maxM,
            colors = SliderDefaults.colors(thumbColor = MathColor, activeTrackColor = MathColor)
          )
        }

        Column {
          Text("Accuracy: ${accuracy.toInt()}%", fontSize = 11.sp, color = BrandEmerald, fontWeight = FontWeight.Bold)
          Slider(
            value = accuracy,
            onValueChange = { accuracy = it },
            valueRange = 50f..100f,
            colors = SliderDefaults.colors(thumbColor = BrandEmerald, activeTrackColor = BrandEmerald)
          )
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onSubmit(testName, examType, phyScore.toInt(), chemScore.toInt(), mathScore.toInt(), accuracy.toDouble())
        },
        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary, contentColor = Color.Black)
      ) {
        Text("Save Score & Update Charts 📊", fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      OutlinedButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}
