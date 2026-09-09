package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Chapter
import com.example.data.ChapterStatus
import com.example.data.SubjectType
import com.example.data.SyllabusTopic
import com.example.data.SyllabusViewMode
import com.example.data.TopicFilterMode
import com.example.data.WeightageLevel
import com.example.ui.components.ChapterCardItem
import com.example.ui.components.FlatTopicChecklistItem
import com.example.ui.components.FormulaCardItem
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel

@Composable
fun SyllabusScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  var selectedChapterForFormulas by remember { mutableStateOf<Chapter?>(null) }
  var chapterForAddingTopic by remember { mutableStateOf<Chapter?>(null) }
  var newTopicName by remember { mutableStateOf("") }
  var newTopicIsHighYield by remember { mutableStateOf(false) }
  var newTopicFormulaHint by remember { mutableStateOf("") }

  // Filter Chapters for selected subject
  val subjectChapters = uiState.chapters.filter { it.subject == uiState.selectedSubjectTab }

  val filteredChapters = subjectChapters.filter { ch ->
    val matchesClass = (uiState.chapterClassFilter == null || ch.classLevel == uiState.chapterClassFilter)
    val matchesWeightage = (uiState.chapterWeightageFilter == null || ch.weightage == uiState.chapterWeightageFilter)
    val matchesQuery = uiState.chapterSearchQuery.isBlank() ||
      ch.name.contains(uiState.chapterSearchQuery, ignoreCase = true) ||
      ch.keyConcepts.any { it.contains(uiState.chapterSearchQuery, ignoreCase = true) } ||
      ch.topics.any { it.name.contains(uiState.chapterSearchQuery, ignoreCase = true) }

    val matchesTopicStatus = when (uiState.topicFilterMode) {
      TopicFilterMode.ALL -> true
      TopicFilterMode.PENDING -> ch.topics.any { !it.isCompleted }
      TopicFilterMode.COMPLETED -> ch.topics.any { it.isCompleted }
      TopicFilterMode.HIGH_YIELD -> ch.topics.any { it.isHighYield }
    }

    matchesClass && matchesWeightage && matchesQuery && matchesTopicStatus
  }

  // All topics in subject for stats
  val allSubjectTopics = subjectChapters.flatMap { it.topics }
  val completedSubjectTopicsCount = allSubjectTopics.count { it.isCompleted }
  val totalSubjectTopicsCount = allSubjectTopics.size
  val subjectProgressFraction = if (totalSubjectTopicsCount > 0) completedSubjectTopicsCount.toFloat() / totalSubjectTopicsCount.toFloat() else 0f
  
  val highYieldTopics = allSubjectTopics.filter { it.isHighYield }
  val completedHighYieldCount = highYieldTopics.count { it.isCompleted }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("syllabus_screen")
  ) {
    // 1. Subject Tab Row (Physics, Chemistry, Mathematics)
    TabRow(
      selectedTabIndex = uiState.selectedSubjectTab.ordinal,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = uiState.selectedSubjectTab.color,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedSubjectTab.ordinal]),
          color = uiState.selectedSubjectTab.color,
          height = 3.dp
        )
      }
    ) {
      SubjectType.entries.forEach { subject ->
        val isSelected = uiState.selectedSubjectTab == subject
        val subTopics = uiState.chapters.filter { it.subject == subject }.flatMap { it.topics }
        val doneCount = subTopics.count { it.isCompleted }
        val allCount = subTopics.size
        val subPercent = if (allCount > 0) (doneCount * 100) / allCount else 0

        Tab(
          selected = isSelected,
          onClick = { viewModel.setSelectedSubjectTab(subject) },
          text = {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.padding(vertical = 4.dp)
            ) {
              Text(
                text = subject.displayName,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) subject.color else MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "$subPercent% done",
                style = MaterialTheme.typography.labelSmall,
                fontSize = 10.sp,
                color = if (isSelected) subject.color.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
              )
            }
          }
        )
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      item { Spacer(modifier = Modifier.height(2.dp)) }

      // Offline Cache Status Banner
      item {
        Surface(
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, BrandEmerald.copy(alpha = 0.35f)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("offline_cache_banner")
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(10.dp)
                  .clip(CircleShape)
                  .background(BrandEmerald)
              )
              Column {
                Text(
                  text = "Room DB Offline Cached",
                  style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                  color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                  text = "${uiState.offlineCacheInfo.totalChaptersCached} chapters • ${uiState.offlineCacheInfo.totalTopicsCached} topics saved locally",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = BrandEmerald.copy(alpha = 0.15f)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Offline Synced",
                  tint = BrandEmerald,
                  modifier = Modifier.size(13.dp)
                )
                Text(
                  text = "100% Offline Ready",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 10.sp),
                  color = BrandEmerald
                )
              }
            }
          }
        }
      }

      // 2. Search Field
      item {
        OutlinedTextField(
          value = uiState.chapterSearchQuery,
          onValueChange = { viewModel.setChapterSearchQuery(it) },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("chapter_search_input"),
          placeholder = { Text("Search topics, chapters, formulas...") },
          leadingIcon = {
            Icon(
              imageVector = Icons.Default.Search,
              contentDescription = "Search",
              tint = uiState.selectedSubjectTab.color
            )
          },
          trailingIcon = {
            if (uiState.chapterSearchQuery.isNotEmpty()) {
              IconButton(onClick = { viewModel.setChapterSearchQuery("") }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
              }
            }
          },
          shape = RoundedCornerShape(14.dp),
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = uiState.selectedSubjectTab.color,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
          )
        )
      }

      // 3. View Mode Selector & Quick Batch Actions Bar
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // View Mode Toggle (Accordion vs Flat Checklist)
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            ViewModeButton(
              title = "Chapters",
              icon = Icons.Default.ViewAgenda,
              isSelected = uiState.syllabusViewMode == SyllabusViewMode.CHAPTER_VIEW,
              accentColor = uiState.selectedSubjectTab.color,
              onClick = { viewModel.setSyllabusViewMode(SyllabusViewMode.CHAPTER_VIEW) }
            )
            ViewModeButton(
              title = "Checklist",
              icon = Icons.Default.ViewList,
              isSelected = uiState.syllabusViewMode == SyllabusViewMode.CHECKLIST_VIEW,
              accentColor = uiState.selectedSubjectTab.color,
              onClick = { viewModel.setSyllabusViewMode(SyllabusViewMode.CHECKLIST_VIEW) }
            )
          }

          // Expand / Collapse / Mark All Action
          if (uiState.syllabusViewMode == SyllabusViewMode.CHAPTER_VIEW) {
            val allExpanded = filteredChapters.isNotEmpty() && filteredChapters.all { uiState.expandedChapterIds.contains(it.id) }
            Text(
              text = if (allExpanded) "Collapse All" else "Expand All",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = uiState.selectedSubjectTab.color,
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { viewModel.expandAllChapters(!allExpanded) }
                .padding(horizontal = 8.dp, vertical = 6.dp)
            )
          } else {
            val allMarked = completedSubjectTopicsCount == totalSubjectTopicsCount && totalSubjectTopicsCount > 0
            Text(
              text = if (allMarked) "Reset All" else "Mark All Done",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = BrandEmerald,
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { viewModel.markAllSubjectTopics(uiState.selectedSubjectTab, !allMarked) }
                .padding(horizontal = 8.dp, vertical = 6.dp)
            )
          }
        }
      }

      // 4. Topic Status & Class Filter Chips
      item {
        TopicFilterChipsRow(
          selectedTopicFilter = uiState.topicFilterMode,
          selectedClass = uiState.chapterClassFilter,
          selectedWeightage = uiState.chapterWeightageFilter,
          subjectColor = uiState.selectedSubjectTab.color,
          onSelectTopicFilter = { viewModel.setTopicFilterMode(it) },
          onSelectClass = { viewModel.setChapterClassFilter(it) },
          onSelectWeightage = { viewModel.setChapterWeightageFilter(it) }
        )
      }

      // 5. Progress Summary Card
      item {
        SubjectChecklistSummaryCard(
          subject = uiState.selectedSubjectTab,
          completedTopics = completedSubjectTopicsCount,
          totalTopics = totalSubjectTopicsCount,
          progressFraction = subjectProgressFraction,
          highYieldCompleted = completedHighYieldCount,
          highYieldTotal = highYieldTopics.size,
          onMarkAllCompleted = { viewModel.markAllSubjectTopics(uiState.selectedSubjectTab, true) }
        )
      }

      // 6. Syllabus Content Display (Chapter View or Checklist View)
      if (filteredChapters.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(
                text = "No topics or chapters match the selected filters",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              TextButton(
                onClick = {
                  viewModel.setChapterSearchQuery("")
                  viewModel.setChapterClassFilter(null)
                  viewModel.setChapterWeightageFilter(null)
                  viewModel.setTopicFilterMode(TopicFilterMode.ALL)
                }
              ) {
                Text("Reset All Filters", color = BrandPrimary)
              }
            }
          }
        }
      } else if (uiState.syllabusViewMode == SyllabusViewMode.CHAPTER_VIEW) {
        // Render Chapter Cards with inline expandable topic checklists
        items(filteredChapters, key = { it.id }) { chapter ->
          val isExpanded = uiState.expandedChapterIds.contains(chapter.id)
          val filteredTopics = when (uiState.topicFilterMode) {
            TopicFilterMode.ALL -> chapter.topics
            TopicFilterMode.PENDING -> chapter.topics.filter { !it.isCompleted }
            TopicFilterMode.COMPLETED -> chapter.topics.filter { it.isCompleted }
            TopicFilterMode.HIGH_YIELD -> chapter.topics.filter { it.isHighYield }
          }
          val displayChapter = chapter.copy(topics = filteredTopics)

          ChapterCardItem(
            chapter = displayChapter,
            isExpanded = isExpanded,
            onToggleExpand = { viewModel.toggleChapterExpanded(chapter.id) },
            onCycleStatus = { viewModel.cycleChapterStatus(chapter.id) },
            onIncrementPyqs = { viewModel.incrementChapterPyqs(chapter.id, 5) },
            onViewFormulas = { selectedChapterForFormulas = chapter },
            onToggleTopic = { topicId -> viewModel.toggleTopicCompletion(chapter.id, topicId) },
            onMarkAllTopics = { completed -> viewModel.markAllTopicsInChapter(chapter.id, completed) },
            onAddTopicClick = {
              chapterForAddingTopic = chapter
              newTopicName = ""
              newTopicIsHighYield = false
              newTopicFormulaHint = ""
            }
          )
        }
      } else {
        // Render Granular Flat Checklist of topics across chapters
        filteredChapters.forEach { chapter ->
          val topicsToShow = when (uiState.topicFilterMode) {
            TopicFilterMode.ALL -> chapter.topics
            TopicFilterMode.PENDING -> chapter.topics.filter { !it.isCompleted }
            TopicFilterMode.COMPLETED -> chapter.topics.filter { it.isCompleted }
            TopicFilterMode.HIGH_YIELD -> chapter.topics.filter { it.isHighYield }
          }

          if (topicsToShow.isNotEmpty()) {
            item(key = "header_${chapter.id}") {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 8.dp, bottom = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .clip(CircleShape)
                      .background(chapter.subject.color)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = chapter.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }

                Text(
                  text = "${chapter.completedTopicCount}/${chapter.totalTopicCount}",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = if (chapter.isAllTopicsCompleted) BrandEmerald else chapter.subject.color
                )
              }
            }

            items(topicsToShow, key = { "${chapter.id}_${it.id}" }) { topic ->
              FlatTopicChecklistItem(
                chapterName = chapter.name,
                classLevel = chapter.classLevel,
                topic = topic,
                subjectColor = chapter.subject.color,
                onToggle = { viewModel.toggleTopicCompletion(chapter.id, topic.id) }
              )
            }
          }
        }
      }

      item { Spacer(modifier = Modifier.height(28.dp)) }
    }
  }

  // Add Custom Topic Dialog
  if (chapterForAddingTopic != null) {
    val targetChapter = chapterForAddingTopic!!
    AlertDialog(
      onDismissRequest = { chapterForAddingTopic = null },
      title = {
        Text(
          text = "Add Topic to ${targetChapter.name}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedTextField(
            value = newTopicName,
            onValueChange = { newTopicName = it },
            label = { Text("Topic / Sub-Concept Name") },
            placeholder = { Text("e.g. Rolling on Inclined Plane") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          OutlinedTextField(
            value = newTopicFormulaHint,
            onValueChange = { newTopicFormulaHint = it },
            label = { Text("Key Formula / Hint (Optional)") },
            placeholder = { Text("e.g. a = g sinθ / (1 + k²/R²)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { newTopicIsHighYield = !newTopicIsHighYield }
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = newTopicIsHighYield,
              onCheckedChange = { newTopicIsHighYield = it },
              colors = CheckboxDefaults.colors(checkedColor = BrandAccentGold)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Mark as High-Yield / PYQ Hotspot 🔥",
              style = MaterialTheme.typography.bodySmall,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.addCustomTopic(targetChapter.id, newTopicName, newTopicIsHighYield, newTopicFormulaHint)
            chapterForAddingTopic = null
          },
          enabled = newTopicName.isNotBlank(),
          colors = ButtonDefaults.buttonColors(containerColor = targetChapter.subject.color)
        ) {
          Text("Add Topic", fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { chapterForAddingTopic = null }) {
          Text("Cancel")
        }
      }
    )
  }

  // Formula Quick Drawer / Dialog
  if (selectedChapterForFormulas != null) {
    val chapter = selectedChapterForFormulas!!
    val relatedFormulas = uiState.formulas.filter { f ->
      f.subject == chapter.subject
    }.take(3)

    AlertDialog(
      onDismissRequest = { selectedChapterForFormulas = null },
      title = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = chapter.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${chapter.subject.displayName} • Class ${chapter.classLevel}",
              style = MaterialTheme.typography.labelSmall,
              color = chapter.subject.color
            )
          }
          IconButton(onClick = { selectedChapterForFormulas = null }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }
      },
      text = {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Text(
            text = "⚡️ High-Yield Cheat Sheet",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = BrandAccentGold
          )

          if (relatedFormulas.isEmpty()) {
            Text(
              text = "Formula cheat sheet for this chapter is being prepared.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          } else {
            relatedFormulas.forEach { card ->
              FormulaCardItem(
                card = card,
                onToggleBookmark = { viewModel.toggleFormulaBookmark(card.id) }
              )
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { selectedChapterForFormulas = null }) {
          Text("Done", color = BrandPrimary, fontWeight = FontWeight.Bold)
        }
      }
    )
  }
}

@Composable
fun ViewModeButton(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  isSelected: Boolean,
  accentColor: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(if (isSelected) accentColor else Color.Transparent)
      .clickable(onClick = onClick)
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
fun TopicFilterChipsRow(
  selectedTopicFilter: TopicFilterMode,
  selectedClass: Int?,
  selectedWeightage: WeightageLevel?,
  subjectColor: Color,
  onSelectTopicFilter: (TopicFilterMode) -> Unit,
  onSelectClass: (Int?) -> Unit,
  onSelectWeightage: (WeightageLevel?) -> Unit
) {
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // 1. All Topics
    item {
      FilterChipPill(
        title = "All Topics",
        isSelected = selectedTopicFilter == TopicFilterMode.ALL && selectedClass == null && selectedWeightage == null,
        accentColor = subjectColor,
        onClick = {
          onSelectTopicFilter(TopicFilterMode.ALL)
          onSelectClass(null)
          onSelectWeightage(null)
        }
      )
    }

    // 2. Pending / To-Do Only
    item {
      FilterChipPill(
        title = "⏳ To-Do Only",
        isSelected = selectedTopicFilter == TopicFilterMode.PENDING,
        accentColor = BrandAccentGold,
        onClick = {
          onSelectTopicFilter(if (selectedTopicFilter == TopicFilterMode.PENDING) TopicFilterMode.ALL else TopicFilterMode.PENDING)
        }
      )
    }

    // 3. Completed Only
    item {
      FilterChipPill(
        title = "✅ Completed",
        isSelected = selectedTopicFilter == TopicFilterMode.COMPLETED,
        accentColor = BrandEmerald,
        onClick = {
          onSelectTopicFilter(if (selectedTopicFilter == TopicFilterMode.COMPLETED) TopicFilterMode.ALL else TopicFilterMode.COMPLETED)
        }
      )
    }

    // 4. High Yield Hotspots Only
    item {
      FilterChipPill(
        title = "🔥 High Yield Only",
        isSelected = selectedTopicFilter == TopicFilterMode.HIGH_YIELD,
        accentColor = BrandPrimary,
        onClick = {
          onSelectTopicFilter(if (selectedTopicFilter == TopicFilterMode.HIGH_YIELD) TopicFilterMode.ALL else TopicFilterMode.HIGH_YIELD)
        }
      )
    }

    // 5. Class 11
    item {
      FilterChipPill(
        title = "Class 11",
        isSelected = selectedClass == 11,
        accentColor = subjectColor,
        onClick = { onSelectClass(if (selectedClass == 11) null else 11) }
      )
    }

    // 6. Class 12
    item {
      FilterChipPill(
        title = "Class 12",
        isSelected = selectedClass == 12,
        accentColor = subjectColor,
        onClick = { onSelectClass(if (selectedClass == 12) null else 12) }
      )
    }

    // 7. High Weightage Chapters
    item {
      FilterChipPill(
        title = "⭐️ High Weightage",
        isSelected = selectedWeightage == WeightageLevel.HIGH,
        accentColor = BrandAccentGold,
        onClick = { onSelectWeightage(if (selectedWeightage == WeightageLevel.HIGH) null else WeightageLevel.HIGH) }
      )
    }
  }
}

@Composable
fun FilterChipPill(
  title: String,
  isSelected: Boolean,
  accentColor: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(20.dp))
      .background(if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant)
      .clickable(onClick = onClick)
      .padding(horizontal = 12.dp, vertical = 6.dp)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
fun SubjectChecklistSummaryCard(
  subject: SubjectType,
  completedTopics: Int,
  totalTopics: Int,
  progressFraction: Float,
  highYieldCompleted: Int,
  highYieldTotal: Int,
  onMarkAllCompleted: () -> Unit
) {
  val percent = (progressFraction * 100).toInt()
  val hyPercent = if (highYieldTotal > 0) (highYieldCompleted * 100) / highYieldTotal else 0

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
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
        Column {
          Text(
            text = "${subject.displayName} Checklist Coverage",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "$completedTopics of $totalTopics topics marked completed",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(subject.color.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "$percent%",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = subject.color
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      LinearProgressIndicator(
        progress = { progressFraction },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = if (progressFraction == 1f) BrandEmerald else subject.color,
        trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Secondary Stats Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "🔥 High-Yield Hotspots: ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Text(
            text = "$highYieldCompleted/$highYieldTotal ($hyPercent%)",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandAccentGold
          )
        }

        if (percent < 100) {
          Text(
            text = "${totalTopics - completedTopics} topics left",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        } else {
          Text(
            text = "🎉 Full Subject Covered!",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandEmerald
          )
        }
      }
    }
  }
}
