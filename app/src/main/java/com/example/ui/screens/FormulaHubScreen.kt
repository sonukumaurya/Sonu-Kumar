package com.example.ui.screens

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
  val filteredFormulas = uiState.formulas.filter { f ->
    (uiState.formulaSubjectFilter == null || f.subject == uiState.formulaSubjectFilter) &&
      (!uiState.formulaOnlyBookmarked || f.isBookmarked) &&
      (uiState.formulaSearchQuery.isBlank() || f.title.contains(uiState.formulaSearchQuery, ignoreCase = true) || f.formula.contains(uiState.formulaSearchQuery, ignoreCase = true) || f.topic.contains(uiState.formulaSearchQuery, ignoreCase = true))
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
      .testTag("formula_hub_screen"),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

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
              text = "150+ High-Yield Formulas for Physics, Chemistry & Math",
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
        // All
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

        // Physics
        item {
          FilterPill(
            title = "Physics",
            isSelected = uiState.formulaSubjectFilter == SubjectType.PHYSICS,
            accentColor = SubjectType.PHYSICS.color,
            onClick = { viewModel.setFormulaSubjectFilter(SubjectType.PHYSICS) }
          )
        }

        // Chemistry
        item {
          FilterPill(
            title = "Chemistry",
            isSelected = uiState.formulaSubjectFilter == SubjectType.CHEMISTRY,
            accentColor = SubjectType.CHEMISTRY.color,
            onClick = { viewModel.setFormulaSubjectFilter(SubjectType.CHEMISTRY) }
          )
        }

        // Mathematics
        item {
          FilterPill(
            title = "Mathematics",
            isSelected = uiState.formulaSubjectFilter == SubjectType.MATHEMATICS,
            accentColor = SubjectType.MATHEMATICS.color,
            onClick = { viewModel.setFormulaSubjectFilter(SubjectType.MATHEMATICS) }
          )
        }

        // Bookmarks only
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

  // Formula Quiz Dialog
  if (uiState.isQuizActive && uiState.activeQuiz.isNotEmpty()) {
    FormulaQuizDialog(
      questions = uiState.activeQuiz,
      currentScore = uiState.quizScore,
      onAnswer = { qIdx, optIdx -> viewModel.answerQuizQuestion(qIdx, optIdx) },
      onDismiss = { viewModel.exitQuiz() }
    )
  }
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
  onAnswer: (questionIndex: Int, optionIndex: Int) -> Unit,
  onDismiss: () -> Unit
) {
  var currentQuestionIndex by remember { mutableStateOf(0) }
  val currentQ = questions.getOrNull(currentQuestionIndex) ?: return
  val totalQuestions = questions.size

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "Formula Quiz 🧠", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Spacer(modifier = Modifier.width(8.dp))
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(BrandEmerald.copy(alpha = 0.2f))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(text = "Score: $currentScore/$totalQuestions", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = BrandEmerald)
          }
        }
        IconButton(onClick = onDismiss) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "Question ${currentQuestionIndex + 1} of $totalQuestions",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontWeight = FontWeight.Bold
        )

        Text(
          text = currentQ.questionText,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Options List
        currentQ.options.forEachIndexed { optIndex, optionText ->
          val isSelected = currentQ.selectedOptionIndex == optIndex
          val isCorrect = optIndex == currentQ.correctOptionIndex
          val optionBgColor = when {
            !currentQ.isAnswered -> MaterialTheme.colorScheme.surfaceVariant
            isCorrect -> BrandEmerald.copy(alpha = 0.25f)
            isSelected -> BrandRose.copy(alpha = 0.25f)
            else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          }
          val borderColor = when {
            !currentQ.isAnswered -> Color.Transparent
            isCorrect -> BrandEmerald
            isSelected -> BrandRose
            else -> Color.Transparent
          }

          OutlinedCard(
            modifier = Modifier
              .fillMaxWidth()
              .clickable(enabled = !currentQ.isAnswered) { onAnswer(currentQuestionIndex, optIndex) },
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.outlinedCardColors(containerColor = optionBgColor),
            border = BorderStroke(1.dp, borderColor)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${('A' + optIndex)}. ",
                style = MaterialTheme.typography.labelMedium,
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
