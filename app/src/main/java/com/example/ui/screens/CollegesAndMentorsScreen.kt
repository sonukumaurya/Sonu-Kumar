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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.data.College
import com.example.data.Mentor
import com.example.ui.components.CollegeCardItem
import com.example.ui.components.MentorCardItem
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.viewmodel.JeePrepUiState
import com.example.ui.viewmodel.JeePrepViewModel

@Composable
fun CollegesAndMentorsScreen(
  uiState: JeePrepUiState,
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Colleges & Compare, 1: Mentors & Chat
  var showCompareDialog by remember { mutableStateOf(false) }

  if (uiState.activeChatMentor != null) {
    MentorChatView(
      mentor = uiState.activeChatMentor,
      messages = uiState.chatMessages[uiState.activeChatMentor.id] ?: emptyList(),
      onSendMessage = { text -> viewModel.sendChatMessage(uiState.activeChatMentor.id, text) },
      onBack = { viewModel.setActiveChatMentor(null) }
    )
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("colleges_mentors_screen")
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
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Colleges & Cutoffs", fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium)
          }
        }
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        text = {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.People, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("IITian Mentors", fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium)
          }
        }
      )
    }

    if (selectedTab == 0) {
      CollegesTabContent(
        colleges = uiState.colleges,
        onViewDetails = { viewModel.setSelectedCollegeForDetail(it) },
        onCompare = {
          viewModel.setCompareColleges(uiState.colleges.getOrNull(0), uiState.colleges.getOrNull(1))
          showCompareDialog = true
        }
      )
    } else {
      MentorsTabContent(
        mentors = uiState.mentors,
        onViewProfile = { viewModel.setSelectedMentorForDetail(it) },
        onStartChat = { viewModel.setActiveChatMentor(it) }
      )
    }
  }

  // College Detail Dialog
  if (uiState.selectedCollegeForDetail != null) {
    CollegeDetailDialog(
      college = uiState.selectedCollegeForDetail,
      onDismiss = { viewModel.setSelectedCollegeForDetail(null) }
    )
  }

  // College Compare Dialog
  if (showCompareDialog && uiState.compareCollege1 != null && uiState.compareCollege2 != null) {
    CollegeCompareDialog(
      college1 = uiState.compareCollege1,
      college2 = uiState.compareCollege2,
      allColleges = uiState.colleges,
      onSelectC1 = { viewModel.setCompareColleges(it, uiState.compareCollege2) },
      onSelectC2 = { viewModel.setCompareColleges(uiState.compareCollege1, it) },
      onDismiss = { showCompareDialog = false }
    )
  }

  // Mentor Profile Dialog
  if (uiState.selectedMentorForDetail != null) {
    MentorDetailDialog(
      mentor = uiState.selectedMentorForDetail,
      onStartChat = {
        val m = uiState.selectedMentorForDetail
        viewModel.setSelectedMentorForDetail(null)
        viewModel.setActiveChatMentor(m)
      },
      onDismiss = { viewModel.setSelectedMentorForDetail(null) }
    )
  }
}

@Composable
fun CollegesTabContent(
  colleges: List<College>,
  onViewDetails: (College) -> Unit,
  onCompare: (College) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Dream Colleges Explorer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Placements, NIRF Rankings & Opening/Closing Ranks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    items(colleges) { college ->
      CollegeCardItem(
        college = college,
        onViewDetails = { onViewDetails(college) },
        onCompare = { onCompare(college) }
      )
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }
}

@Composable
fun MentorsTabContent(
  mentors: List<Mentor>,
  onViewProfile: (Mentor) -> Unit,
  onStartChat: (Mentor) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item { Spacer(modifier = Modifier.height(4.dp)) }

    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, BrandSecondary.copy(alpha = 0.4f))
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(BrandSecondary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "🎓", fontSize = 22.sp)
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Learn from Top 500 AIR Rankers",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = BrandSecondary
            )
            Text(
              text = "Get proven revision schedules, backlog remedies, and direct guidance from students who cracked JEE Advanced.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    items(mentors) { mentor ->
      MentorCardItem(
        mentor = mentor,
        onViewProfile = { onViewProfile(mentor) },
        onStartChat = { onStartChat(mentor) }
      )
    }

    item { Spacer(modifier = Modifier.height(24.dp)) }
  }
}

@Composable
fun MentorChatView(
  mentor: Mentor,
  messages: List<ChatMessage>,
  onSendMessage: (String) -> Unit,
  onBack: () -> Unit
) {
  var messageInput by remember { mutableStateOf("") }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .testTag("mentor_chat_view")
  ) {
    // Chat Header
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 2.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack) {
          Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(BrandPrimary.copy(alpha = 0.25f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = mentor.name.take(1),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = BrandPrimary
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = mentor.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "AIR ${mentor.airRank} • ${mentor.college}",
            style = MaterialTheme.typography.labelSmall,
            color = BrandEmerald,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    // Messages Area
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      item { Spacer(modifier = Modifier.height(10.dp)) }

      // Welcome advice banner
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "📌 ${mentor.name}'s Core Advice:",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = BrandAccentGold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "\"${mentor.topAdvice}\"",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      }

      items(messages) { msg ->
        ChatBubble(message = msg)
      }

      item { Spacer(modifier = Modifier.height(10.dp)) }
    }

    // Message Input Field
    Surface(
      color = MaterialTheme.colorScheme.surface,
      shadowElevation = 4.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = messageInput,
          onValueChange = { messageInput = it },
          placeholder = { Text("Ask your doubt (e.g. backlog, calculus tips)...") },
          modifier = Modifier
            .weight(1f)
            .testTag("chat_input_field"),
          shape = RoundedCornerShape(24.dp),
          maxLines = 3,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrandPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
          )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            if (messageInput.isNotBlank()) {
              onSendMessage(messageInput)
              messageInput = ""
            }
          },
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(BrandPrimary)
            .testTag("send_message_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}

@Composable
fun ChatBubble(message: ChatMessage) {
  val isUser = message.isFromUser

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
  ) {
    Box(
      modifier = Modifier
        .clip(
          RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = if (isUser) 16.dp else 4.dp,
            bottomEnd = if (isUser) 4.dp else 16.dp
          )
        )
        .background(
          if (isUser) BrandPrimary else MaterialTheme.colorScheme.surfaceVariant
        )
        .padding(horizontal = 14.dp, vertical = 10.dp)
        .fillMaxWidth(0.85f)
    ) {
      Column {
        Text(
          text = message.text,
          style = MaterialTheme.typography.bodyMedium,
          color = if (isUser) Color.Black else MaterialTheme.colorScheme.onSurface,
          lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = message.timestamp,
          style = MaterialTheme.typography.labelSmall,
          color = if (isUser) Color.Black.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 10.sp,
          modifier = Modifier.align(Alignment.End)
        )
      }
    }
  }
}

@Composable
fun CollegeDetailDialog(
  college: College,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(text = college.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text(text = "NIRF Rank #${college.nirfRank} • ${college.location}", style = MaterialTheme.typography.labelSmall, color = BrandPrimary)
        }
        IconButton(onClick = onDismiss) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Placement & Cutoff Highlight Box
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(text = "📊 Placement & Cutoff Snapshot", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = BrandEmerald)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "• Average CTC: ₹${college.avgPackageLpa} LPA", style = MaterialTheme.typography.bodySmall)
            Text(text = "• Highest Package: ${college.highestPackageLpa}", style = MaterialTheme.typography.bodySmall)
            Text(text = "• CSE Closing AIR: ~${college.cseCutoff}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = BrandAccentGold)
            Text(text = "• Electrical/ECE Closing AIR: ~${college.eceCutoff}", style = MaterialTheme.typography.bodySmall)
            Text(text = "• Mechanical Closing AIR: ~${college.mechCutoff}", style = MaterialTheme.typography.bodySmall)
          }
        }

        Text(text = "Campus Culture & Highlights", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        college.campusHighlights.forEach { highlight ->
          Text(text = "✨ $highlight", style = MaterialTheme.typography.bodySmall)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Top Engineering Branches", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        Text(text = college.keyBranches.joinToString(", "), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Done", color = BrandPrimary, fontWeight = FontWeight.Bold)
      }
    }
  )
}

@Composable
fun CollegeCompareDialog(
  college1: College,
  college2: College,
  allColleges: List<College>,
  onSelectC1: (College) -> Unit,
  onSelectC2: (College) -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = "Side-by-Side Comparison ⚖️", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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
        // Headers
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = college1.shortName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
            color = BrandPrimary,
            modifier = Modifier.weight(1f)
          )
          Text(
            text = "VS",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = BrandAccentGold,
            modifier = Modifier.padding(horizontal = 8.dp)
          )
          Text(
            text = college2.shortName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
            color = BrandSecondary,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
          )
        }

        // Rows
        CompareDataRow(title = "NIRF Ranking", v1 = "#${college1.nirfRank}", v2 = "#${college2.nirfRank}")
        CompareDataRow(title = "Average CTC", v1 = "₹${college1.avgPackageLpa} LPA", v2 = "₹${college2.avgPackageLpa} LPA")
        CompareDataRow(title = "CSE Cutoff", v1 = "AIR < ${college1.cseCutoff}", v2 = "AIR < ${college2.cseCutoff}")
        CompareDataRow(title = "ECE Cutoff", v1 = "AIR < ${college1.eceCutoff}", v2 = "AIR < ${college2.eceCutoff}")
        CompareDataRow(title = "Established", v1 = "${college1.established}", v2 = "${college2.established}")
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Done", color = BrandPrimary, fontWeight = FontWeight.Bold)
      }
    }
  )
}

@Composable
fun CompareDataRow(
  title: String,
  v1: String,
  v2: String
) {
  Card(
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = v1, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrandPrimary, modifier = Modifier.weight(1f))
      Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
      Text(text = v2, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = BrandSecondary, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.End)
    }
  }
}

@Composable
fun MentorDetailDialog(
  mentor: Mentor,
  onStartChat: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(text = mentor.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
          Text(text = "AIR ${mentor.airRank} • ${mentor.college} (${mentor.branch})", style = MaterialTheme.typography.labelSmall, color = BrandAccentGold, fontWeight = FontWeight.Bold)
        }
        IconButton(onClick = onDismiss) {
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
          text = "🎯 JEE Advanced Scores:",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = BrandPrimary
        )
        Text(
          text = "• Physics: ${mentor.physicsScore}/120\n• Chemistry: ${mentor.chemistryScore}/120\n• Mathematics: ${mentor.mathScore}/120",
          style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "⏰ Daily Preparation Timetable:",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = BrandEmerald
        )
        Text(
          text = mentor.timetable,
          style = MaterialTheme.typography.bodySmall,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "💡 Advice for Next 3-6 Months:",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = BrandAccentGold
        )
        Text(
          text = mentor.topAdvice,
          style = MaterialTheme.typography.bodySmall,
          lineHeight = 18.sp
        )
      }
    },
    confirmButton = {
      Button(
        onClick = onStartChat,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BrandPrimary)
      ) {
        Text("Start Chat 💬", color = Color.Black, fontWeight = FontWeight.Bold)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Close")
      }
    }
  )
}
