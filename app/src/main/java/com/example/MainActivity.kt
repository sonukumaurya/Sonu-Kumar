package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.CollegesAndMentorsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FormulaHubScreen
import com.example.ui.screens.ProfileSettingsScreen
import com.example.ui.screens.RankPredictorScreen
import com.example.ui.screens.StudyTrackerScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.BrandAccentGold
import com.example.ui.theme.BrandEmerald
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.JEEPrepTheme
import com.example.ui.viewmodel.JeePrepViewModel
import com.example.notification.NotificationHelper

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Initialize Notification Channel for local study reminders and Avengers motivation
    NotificationHelper.createNotificationChannel(applicationContext)

    setContent {
      val context = LocalContext.current
      val viewModel: JeePrepViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
          override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return JeePrepViewModel(context.applicationContext as Application) as T
          }
        }
      )
      val uiState by viewModel.uiState.collectAsState()

      JEEPrepTheme(themeMode = uiState.themeMode) {
        JeePrepApp(
          viewModel = viewModel
        )
      }
    }
  }
}

enum class MainNavigationTab(
  val title: String,
  val icon: ImageVector,
  val testTag: String
) {
  DASHBOARD("Home", Icons.Default.Dashboard, "nav_tab_dashboard"),
  SYLLABUS("Syllabus", Icons.Default.MenuBook, "nav_tab_syllabus"),
  FOCUS_TIMER("Focus", Icons.Default.Timer, "nav_tab_timer"),
  AIR_SIMULATOR("AIR Jump", Icons.Default.TrendingUp, "nav_tab_simulator"),
  FORMULAS("Formulas", Icons.Default.AutoAwesome, "nav_tab_formulas")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JeePrepApp(
  viewModel: JeePrepViewModel,
  modifier: Modifier = Modifier
) {
  val uiState by viewModel.uiState.collectAsState()
  var currentTabIndex by remember { mutableIntStateOf(0) }

  val visibleTabs = listOf(
    MainNavigationTab.DASHBOARD,
    MainNavigationTab.SYLLABUS,
    MainNavigationTab.FOCUS_TIMER,
    MainNavigationTab.AIR_SIMULATOR,
    MainNavigationTab.FORMULAS
  )

  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      CenterAlignedTopAppBar(
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BrandPrimary),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "JEE",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = when (currentTabIndex) {
                  0 -> "JEE Prep 2026/27"
                  1 -> "Syllabus Mastery"
                  2 -> "Focus Pomodoro"
                  3 -> "AIR & Score Simulator"
                  4 -> "Formula Vault"
                  5 -> "Colleges & Mentors"
                  6 -> "Settings & Themes"
                  7 -> "Visual Analytics & Progress"
                  else -> "JEE Prep"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
              )
            }
          }
        },
        actions = {
          // Quick Visual Analytics & Recharts Progress Dashboard icon
          IconButton(
            onClick = { currentTabIndex = 7 },
            modifier = Modifier.testTag("top_analytics_button")
          ) {
            Icon(
              imageVector = Icons.Default.TrendingUp,
              contentDescription = "Visual Analytics",
              tint = if (currentTabIndex == 7) BrandEmerald else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          // Quick IITian Mentors / Colleges icon
          IconButton(
            onClick = { currentTabIndex = 5 },
            modifier = Modifier.testTag("top_colleges_button")
          ) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = "Colleges & Mentors",
              tint = if (currentTabIndex == 5) BrandPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          // Settings / Theme Switcher icon
          IconButton(
            onClick = { currentTabIndex = 6 },
            modifier = Modifier.testTag("top_settings_button")
          ) {
            Icon(
              imageVector = if (uiState.themeMode == AppThemeMode.OLED_BLACK) Icons.Default.DarkMode else Icons.Default.Settings,
              contentDescription = "Settings",
              tint = if (currentTabIndex == 6) BrandAccentGold else MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 8.dp
      ) {
        visibleTabs.forEachIndexed { index, tab ->
          val isSelected = currentTabIndex == index
          NavigationBarItem(
            selected = isSelected,
            onClick = { currentTabIndex = index },
            icon = {
              Icon(
                imageVector = tab.icon,
                contentDescription = tab.title,
                modifier = Modifier.size(22.dp)
              )
            },
            label = {
              Text(
                text = tab.title,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = Color.Black,
              selectedTextColor = BrandPrimary,
              indicatorColor = BrandPrimary,
              unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
              unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.testTag(tab.testTag)
          )
        }
      }
    }
  ) { innerPadding ->
    BoxWithConstraints(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .widthIn(max = 720.dp)
          .align(Alignment.Center)
      ) {
        when (currentTabIndex) {
          0 -> DashboardScreen(
            uiState = uiState,
            viewModel = viewModel,
            onNavigateToTab = { tabIdx -> currentTabIndex = tabIdx },
            onNavigateToSyllabusWithSubject = { subject ->
              currentTabIndex = 1
            }
          )
          1 -> SyllabusScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          2 -> StudyTrackerScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          3 -> RankPredictorScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          4 -> FormulaHubScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          5 -> CollegesAndMentorsScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          6 -> ProfileSettingsScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          7 -> AnalyticsScreen(
            uiState = uiState,
            viewModel = viewModel
          )
          else -> DashboardScreen(
            uiState = uiState,
            viewModel = viewModel,
            onNavigateToTab = { tabIdx -> currentTabIndex = tabIdx },
            onNavigateToSyllabusWithSubject = { currentTabIndex = 1 }
          )
        }
      }
    }
  }
}
