package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FormulaCard
import com.example.data.SavedFormulaSheet
import com.example.data.SubjectType
import com.example.ui.components.FormulaCardItem
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandRose
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.FormulaQuizQuestion
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel

@Composable
fun FormulaHubScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  var sheetSubjectFilter by remember { mutableStateOf<SubjectType?>(null) }

  val filteredFormulas = uiState.formulas.filter { f ->
    (uiState.formulaSubjectFilter == null || f.subject == uiState.formulaSubjectFilter) &&
      (!uiState.formulaOnlyBookmarked || f.isBookmarked) &&
      (uiState.formulaSearchQuery.isBlank() ||
        f.title.contains(uiState.formulaSearchQuery, ignoreCase = true) ||
        f.formula.contains(uiState.formulaSearchQuery, ignoreCase = true) ||
        f.topic.contains(uiState.formulaSearchQuery, ignoreCase = true))
  }

  val filteredFormulaSheets = uiState.savedFormulaSheets.filter { sheet ->
    (sheetSubjectFilter == null || sheet.subject == sheetSubjectFilter) &&
      (uiState.formulaSearchQuery.isBlank() ||
        sheet.title.contains(uiState.formulaSearchQuery, ignoreCase = true) ||
        sheet.description.contains(uiState.formulaSearchQuery, ignoreCase = true) ||
        sheet.category.contains(uiState.formulaSearchQuery, ignoreCase = true))
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("formula_hub_screen")
  ) {
    // Top Tabs: All Formulas vs Saved Formula Sheets (Offline Vault)
    TabRow(
      selectedTabIndex = uiState.selectedFormulaTab,
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = BrandPrimary,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.selectedFormulaTab]),
          color = BrandPrimary,
          height = 3.dp
        )
      }
    ) {
      Tab(
        selected = uiState.selectedFormulaTab == 0,
        onClick = { viewModel.setSelectedFormulaTab(0) },
        text = {
          Text(
            text = "All Formulas (${uiState.formulas.size})",
            fontWeight = if (uiState.selectedFormulaTab == 0) FontWeight.Bold else FontWeight.Medium,
            color = if (uiState.selectedFormulaTab == 0) BrandPrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      )
      Tab(
        selected = uiState.selectedFormulaTab == 1,
        onClick = { viewModel.setSelectedFormulaTab(1) },
        text = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.CloudDone,
              contentDescription = null,
              tint = if (uiState.selectedFormulaTab == 1) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = "Saved Formula Sheets (${uiState.savedFormulaSheets.size})",
              fontWeight = if (uiState.selectedFormulaTab == 1) FontWeight.Bold else FontWeight.Medium,
              color = if (uiState.selectedFormulaTab == 1) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      )
    }

    if (uiState.selectedFormulaTab == 0) {
      // TAB 0: ALL FORMULAS
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Offline Room DB Quick Status Banner
        item {
          Surface(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BrandEmerald.copy(alpha = 0.35f)),
            modifier = Modifier.fillMaxWidth()
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
                    text = "Room DB Offline Cache Active",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "${uiState.formulas.size} formulas & ${uiState.savedFormulaSheets.size} sheets cached in SQLite",
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
                    contentDescription = "Cached",
                    tint = BrandEmerald,
                    modifier = Modifier.size(13.dp)
                  )
                  Text(
                    text = "Offline Ready",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 10.sp),
                    color = BrandEmerald
                  )
                }
              }
            }
          }
        }

        // Header & Quiz Button Banner
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, BrandAccentGold.copy(alpha = 0.4f))
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = BrandAccentGold,
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Formula Vault & Memory Quiz",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "High-Yield Formulas for Physics, Chemistry & Math",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Button(
                onClick = { viewModel.startFormulaQuiz() },
                colors = ButtonDefaults.buttonColors(containerColor = BrandAccentGold),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("start_formula_quiz_button")
              ) {
                Icon(imageVector = Icons.Default.Quiz, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Quiz", color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        // Search Box
        item {
          OutlinedTextField(
            value = uiState.formulaSearchQuery,
            onValueChange = { viewModel.setFormulaSearchQuery(it) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("formula_search_input"),
            placeholder = { Text("Search by formula or topic (e.g. Nernst, Carnot, King)") },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = BrandPrimary
              )
            },
            trailingIcon = {
              if (uiState.formulaSearchQuery.isNotEmpty()) {
                IconButton(onClick = { viewModel.setFormulaSearchQuery("") }) {
                  Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
              }
            },
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = BrandPrimary,
              unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
          )
        }

        // Subject Filters & Bookmarks Toggle
        item {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            item {
              FilterPill(
                title = "All (${uiState.formulas.size})",
                isSelected = uiState.formulaSubjectFilter == null && !uiState.formulaOnlyBookmarked,
                accentColor = BrandPrimary,
                onClick = {
                  viewModel.setFormulaSubjectFilter(null)
                  if (uiState.formulaOnlyBookmarked) viewModel.toggleFormulaOnlyBookmarked()
                }
              )
            }

            item {
              FilterPill(
                title = "Physics",
                isSelected = uiState.formulaSubjectFilter == SubjectType.PHYSICS,
                accentColor = SubjectType.PHYSICS.color,
                onClick = { viewModel.setFormulaSubjectFilter(SubjectType.PHYSICS) }
              )
            }

            item {
              FilterPill(
                title = "Chemistry",
                isSelected = uiState.formulaSubjectFilter == SubjectType.CHEMISTRY,
                accentColor = SubjectType.CHEMISTRY.color,
                onClick = { viewModel.setFormulaSubjectFilter(SubjectType.CHEMISTRY) }
              )
            }

            item {
              FilterPill(
                title = "Mathematics",
                isSelected = uiState.formulaSubjectFilter == SubjectType.MATHEMATICS,
                accentColor = SubjectType.MATHEMATICS.color,
                onClick = { viewModel.setFormulaSubjectFilter(SubjectType.MATHEMATICS) }
              )
            }

            item {
              FilterPill(
                title = "⭐️ Bookmarked",
                isSelected = uiState.formulaOnlyBookmarked,
                accentColor = BrandAccentGold,
                onClick = { viewModel.toggleFormulaOnlyBookmarked() }
              )
            }
          }
        }

        // Formulas List
        if (filteredFormulas.isEmpty()) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "No formulas found matching your filter",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        } else {
          items(filteredFormulas) { card ->
            FormulaCardItem(
              card = card,
              onToggleBookmark = { viewModel.toggleFormulaBookmark(card.id) }
            )
          }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
      }
    } else {
      // TAB 1: SAVED FORMULA SHEETS (OFFLINE VAULT)
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Room DB Offline Vault Banner
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
              containerColor = BrandEmerald.copy(alpha = 0.08f)
            ),
            border = BorderStroke(1.dp, BrandEmerald.copy(alpha = 0.4f))
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
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Storage,
                    contentDescription = null,
                    tint = BrandEmerald,
                    modifier = Modifier.size(22.dp)
                  )
                  Text(
                    text = "Room DB Offline Vault",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }

                Surface(
                  shape = RoundedCornerShape(14.dp),
                  color = BrandEmerald.copy(alpha = 0.2f)
                ) {
                  Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = null,
                      tint = BrandEmerald,
                      modifier = Modifier.size(13.dp)
                    )
                    Text(
                      text = "100% Offline Ready",
                      style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                      color = BrandEmerald
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Text(
                text = "These comprehensive formula sheets are cached locally on your device via Room SQLite Database. Review them anytime during travel, power cuts, or distraction-free airplane mode study sessions.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
              )

              Spacer(modifier = Modifier.height(10.dp))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${uiState.savedFormulaSheets.count { it.isDownloadedOffline }} of ${uiState.savedFormulaSheets.size} sheets cached locally",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.SemiBold,
                  color = BrandEmerald
                )
                Text(
                  text = "Room SQLite • v1.0",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }

        // Subject Filter Pills for Sheets
        item {
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            item {
              FilterPill(
                title = "All Sheets (${uiState.savedFormulaSheets.size})",
                isSelected = sheetSubjectFilter == null,
                accentColor = BrandPrimary,
                onClick = { sheetSubjectFilter = null }
              )
            }

            item {
              FilterPill(
                title = "Physics Sheets",
                isSelected = sheetSubjectFilter == SubjectType.PHYSICS,
                accentColor = SubjectType.PHYSICS.color,
                onClick = { sheetSubjectFilter = SubjectType.PHYSICS }
              )
            }

            item {
              FilterPill(
                title = "Chemistry Sheets",
                isSelected = sheetSubjectFilter == SubjectType.CHEMISTRY,
                accentColor = SubjectType.CHEMISTRY.color,
                onClick = { sheetSubjectFilter = SubjectType.CHEMISTRY }
              )
            }

            item {
              FilterPill(
                title = "Math Sheets",
                isSelected = sheetSubjectFilter == SubjectType.MATHEMATICS,
                accentColor = SubjectType.MATHEMATICS.color,
                onClick = { sheetSubjectFilter = SubjectType.MATHEMATICS }
              )
            }
          }
        }

        // List of Saved Formula Sheets
        if (filteredFormulaSheets.isEmpty()) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "No formula sheets found",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        } else {
          items(filteredFormulaSheets) { sheet ->
            FormulaSheetCardItem(
              sheet = sheet,
              onViewSheet = { viewModel.setSelectedFormulaSheet(sheet) },
              onToggleOffline = { viewModel.toggleSheetOffline(sheet.id) }
            )
          }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
      }
    }
  }

  // Formula Quiz Dialog
  if (uiState.isQuizActive && uiState.activeQuiz.isNotEmpty()) {
    FormulaQuizDialog(
      questions = uiState.activeQuiz,
      currentScore = uiState.quizScore,
      onAnswer = { qIdx, optIdx -> viewModel.answerQuizQuestion(qIdx, optIdx) },
      onDismiss = { viewModel.exitQuiz() }
    )
  }

  // Formula Sheet Viewer Modal
  uiState.selectedFormulaSheet?.let { sheet ->
    FormulaSheetViewerDialog(
      sheet = sheet,
      allFormulas = uiState.formulas,
      onDismiss = { viewModel.setSelectedFormulaSheet(null) }
    )
  }
}

@Composable
fun FormulaSheetCardItem(
  sheet: SavedFormulaSheet,
  onViewSheet: () -> Unit,
  onToggleOffline: () -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedCard(
    modifier = modifier
      .fillMaxWidth()
      .testTag("formula_sheet_${sheet.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = BorderStroke(1.dp, sheet.subject.color.copy(alpha = 0.35f))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Header: Subject Badge + Category Chip
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(sheet.subject.color.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = sheet.subject.displayName,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = sheet.subject.color
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = sheet.category,
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        // Room DB Cached status chip
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (sheet.isDownloadedOffline) BrandEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = if (sheet.isDownloadedOffline) Icons.Default.CloudDone else Icons.Default.FileDownload,
              contentDescription = null,
              tint = if (sheet.isDownloadedOffline) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = if (sheet.isDownloadedOffline) "Room Cached" else "Not Cached",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold),
              color = if (sheet.isDownloadedOffline) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = sheet.title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = sheet.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Metadata row & Action buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Description,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(14.dp)
          )
          Text(
            text = "${sheet.formulaCount} High-Yield Formulas • ${sheet.downloadSizeKb} KB",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedButton(
            onClick = onToggleOffline,
            shape = RoundedCornerShape(10.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
            border = BorderStroke(1.dp, if (sheet.isDownloadedOffline) BrandEmerald.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline)
          ) {
            Icon(
              imageVector = if (sheet.isDownloadedOffline) Icons.Default.Check else Icons.Default.FileDownload,
              contentDescription = null,
              tint = if (sheet.isDownloadedOffline) BrandEmerald else MaterialTheme.colorScheme.onSurface,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (sheet.isDownloadedOffline) "Cached" else "Download",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = if (sheet.isDownloadedOffline) BrandEmerald else MaterialTheme.colorScheme.onSurface
            )
          }

          Button(
            onClick = onViewSheet,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = sheet.subject.color),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Visibility,
              contentDescription = null,
              tint = Color.Black,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "View Sheet",
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
fun FormulaSheetViewerDialog(
  sheet: SavedFormulaSheet,
  allFormulas: List<FormulaCard>,
  onDismiss: () -> Unit
) {
  val clipboardManager = LocalClipboardManager.current
  val context = LocalContext.current

  // Filter formulas belonging to this sheet or matching subject as fallback
  val sheetFormulas = if (sheet.formulaIds.isNotEmpty()) {
    val matched = allFormulas.filter { sheet.formulaIds.contains(it.id) }
    if (matched.isNotEmpty()) matched else allFormulas.filter { it.subject == sheet.subject }
  } else {
    allFormulas.filter { it.subject == sheet.subject }
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(sheet.subject.color.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = sheet.subject.displayName,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = sheet.subject.color
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = BrandEmerald.copy(alpha = 0.15f)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                imageVector = Icons.Default.CloudDone,
                contentDescription = null,
                tint = BrandEmerald,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = "Room DB Offline Cached",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold),
                color = BrandEmerald
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = sheet.title,
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Text(
          text = "${sheetFormulas.size} Formulas • Fully Accessible Without Internet",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    },
    text = {
      LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(sheetFormulas) { formulaItem ->
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
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
                Text(
                  text = formulaItem.title,
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
                IconButton(
                  onClick = {
                    clipboardManager.setText(AnnotatedString("${formulaItem.title}: ${formulaItem.formula}"))
                    Toast.makeText(context, "Formula copied!", Toast.LENGTH_SHORT).show()
                  },
                  modifier = Modifier.size(26.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }

              Text(
                text = formulaItem.topic,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )

              Spacer(modifier = Modifier.height(6.dp))

              Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Text(
                  text = formulaItem.formula,
                  style = MaterialTheme.typography.bodyMedium,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Bold,
                  color = sheet.subject.color,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                )
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = formulaItem.explanation,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )

              if (formulaItem.unitsAndConstants.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Units & Constants: ${formulaItem.unitsAndConstants}",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                  color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
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
        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
      ) {
        Text("Done", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    }
  )
}

@Composable
fun FilterPill(
  title: String,
  isSelected: Boolean,
  accentColor: Color,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(18.dp))
      .background(if (isSelected) accentColor else MaterialTheme.colorScheme.surfaceVariant)
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 6.dp)
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
fun FormulaQuizDialog(
  questions: List<FormulaQuizQuestion>,
  currentScore: Int,
  onAnswer: (Int, Int) -> Unit,
  onDismiss: () -> Unit
) {
  var currentQuestionIndex by remember { mutableIntStateOf(0) }
  val totalQuestions = questions.size
  val currentQ = questions.getOrNull(currentQuestionIndex) ?: return

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Formula Flash Recall",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Q ${currentQuestionIndex + 1}/$totalQuestions",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = currentQ.questionText,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )

        currentQ.options.forEachIndexed { optIdx, optionText ->
          val isSelected = currentQ.selectedOptionIndex == optIdx
          val isCorrect = optIdx == currentQ.correctOptionIndex
          val optionColor = when {
            !currentQ.isAnswered -> MaterialTheme.colorScheme.surfaceVariant
            isCorrect -> BrandEmerald.copy(alpha = 0.25f)
            isSelected -> BrandRose.copy(alpha = 0.25f)
            else -> MaterialTheme.colorScheme.surfaceVariant
          }

          Surface(
            shape = RoundedCornerShape(10.dp),
            color = optionColor,
            border = BorderStroke(
              1.dp,
              if (currentQ.isAnswered && isCorrect) BrandEmerald else Color.Transparent
            ),
            modifier = Modifier
              .fillMaxWidth()
              .clickable(enabled = !currentQ.isAnswered) {
                onAnswer(currentQuestionIndex, optIdx)
              }
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${('A' + optIdx)}. ",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = optionText,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        if (currentQ.isAnswered) {
          Text(
            text = if (currentQ.selectedOptionIndex == currentQ.correctOptionIndex) "✅ Correct! Great recall!" else "❌ Correct formula: ${currentQ.formulaCard.formula}",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = if (currentQ.selectedOptionIndex == currentQ.correctOptionIndex) BrandEmerald else BrandRose
          )
        }
      }
    },
    confirmButton = {
      if (currentQuestionIndex < totalQuestions - 1) {
        Button(
          onClick = { currentQuestionIndex += 1 },
          colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
        ) {
          Text("Next Question →", color = Color.Black, fontWeight = FontWeight.Bold)
        }
      } else {
        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = BrandEmerald)
        ) {
          Text("Complete Quiz ($currentScore/$totalQuestions)", color = Color.Black, fontWeight = FontWeight.Bold)
        }
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Exit")
      }
    }
  )
}
