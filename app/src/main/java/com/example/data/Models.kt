package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.ChemistryColor
import com.example.ui.theme.MathColor
import com.example.ui.theme.PhysicsColor

enum class SubjectType(val displayName: String, val color: Color, val shortCode: String) {
  PHYSICS("Physics", PhysicsColor, "PHY"),
  CHEMISTRY("Chemistry", ChemistryColor, "CHEM"),
  MATHEMATICS("Mathematics", MathColor, "MATH")
}

enum class ChapterStatus(val label: String) {
  NOT_STARTED("Not Started"),
  IN_PROGRESS("In Progress"),
  REVISED("Revised"),
  MASTERED("Mastered")
}

enum class WeightageLevel(val label: String, val badgeColor: Color) {
  HIGH("High Weightage (8-12M)", Color(0xFFF59E0B)),
  MEDIUM("Medium Weightage (4-8M)", Color(0xFF38BDF8)),
  FUNDAMENTAL("Fundamental (4M)", Color(0xFF10B981))
}

enum class TopicFilterMode(val label: String) {
  ALL("All Topics"),
  PENDING("⏳ To-Do Only"),
  COMPLETED("✅ Completed"),
  HIGH_YIELD("🔥 High-Yield Hotspots")
}

enum class SyllabusViewMode(val label: String) {
  CHAPTER_VIEW("Chapter View"),
  CHECKLIST_VIEW("Checklist View")
}

data class SyllabusTopic(
  val id: String,
  val name: String,
  val isCompleted: Boolean = false,
  val isHighYield: Boolean = false,
  val tag: String = "Core",
  val keyFormulaHint: String? = null,
  val notes: String = ""
)

data class Chapter(
  val id: String,
  val subject: SubjectType,
  val name: String,
  val classLevel: Int, // 11 or 12
  val weightage: WeightageLevel,
  val pyqsSolved: Int,
  val totalPyqs: Int,
  val status: ChapterStatus,
  val revisionCount: Int,
  val keyFormulaCount: Int,
  val keyConcepts: List<String>,
  val topics: List<SyllabusTopic> = emptyList()
) {
  val completedTopicCount: Int get() = topics.count { it.isCompleted }
  val totalTopicCount: Int get() = topics.size
  val completedTopicsCount: Int get() = completedTopicCount
  val totalTopicsCount: Int get() = totalTopicCount
  val isAllTopicsCompleted: Boolean get() = topics.isNotEmpty() && topics.all { it.isCompleted }
  val topicCompletionFraction: Float get() = if (topics.isEmpty()) (if (status == ChapterStatus.MASTERED) 1f else 0f) else completedTopicCount.toFloat() / topics.size.toFloat()
}

data class DailyGoal(
  val id: String,
  val title: String,
  val subject: SubjectType,
  val isCompleted: Boolean,
  val durationMinutes: Int,
  val isHighPriority: Boolean = false
)

data class MockTest(
  val id: String,
  val title: String,
  val examType: String, // "JEE Main" or "JEE Advanced"
  val dateFormatted: String,
  val daysRemaining: Int,
  val durationHours: Double,
  val totalMarks: Int,
  val userScore: Int? = null,
  val userPercentile: Double? = null,
  val isRegistered: Boolean = true
)

data class FormulaCard(
  val id: String,
  val subject: SubjectType,
  val topic: String,
  val title: String,
  val formula: String,
  val explanation: String,
  val unitsAndConstants: String,
  val isBookmarked: Boolean = false
)

data class College(
  val id: String,
  val name: String,
  val shortName: String,
  val location: String,
  val nirfRank: Int,
  val established: Int,
  val avgPackageLpa: Double,
  val highestPackageLpa: String,
  val cseCutoff: Int,
  val eceCutoff: Int,
  val mechCutoff: Int,
  val overview: String,
  val keyBranches: List<String>,
  val campusHighlights: List<String>
)

data class CollegeChance(
  val collegeName: String,
  val branchName: String,
  val chancePercentage: Int, // e.g. 92%
  val status: String // "High Chance", "Moderate", "Reach"
)

data class Mentor(
  val id: String,
  val name: String,
  val airRank: Int,
  val college: String,
  val branch: String,
  val tagLine: String,
  val bio: String,
  val physicsScore: Int,
  val chemistryScore: Int,
  val mathScore: Int,
  val topAdvice: String,
  val timetable: String,
  val rating: Double,
  val sessionCount: Int,
  val isAvailable: Boolean = true,
  val avatarSeed: String
)

data class ChatMessage(
  val id: String,
  val mentorId: String,
  val text: String,
  val isFromUser: Boolean,
  val timestamp: String
)

data class StudySession(
  val id: String,
  val timestamp: Long,
  val durationMinutes: Int,
  val subject: SubjectType,
  val chapterName: String
)

data class UserProfile(
  val name: String = "Sonu Kumar",
  val targetYear: Int = 2026,
  val targetAir: Int = 500,
  val currentStreakDays: Int = 18,
  val dreamCollege: String = "IIT Bombay",
  val dreamBranch: String = "Computer Science & Engineering",
  val classLevel: String = "Class 12",
  val category: String = "General (Open)",
  val targetScoreMains: Int = 250,
  val targetScoreAdvanced: Int = 280,
  val avengerRank: String = "Earth's Mightiest Aspirant",
  val arcReactorEnergy: Int = 88 // 0-100%
)

enum class QuoteCategory(val label: String, val emoji: String) {
  ALL("All Quotes", "✨"),
  AVENGERS("Avengers Protocol", "⚡️"),
  GRIT_AND_DISCIPLINE("Grit & Discipline", "🛡️"),
  EXAM_CONFIDENCE("Exam Fearlessness", "🔥"),
  LEGENDS_OF_SCIENCE("Science Pioneers", "🚀")
}

data class MotivationalQuote(
  val id: String,
  val quote: String,
  val authorOrHero: String,
  val heroRole: String,
  val category: QuoteCategory,
  val subtext: String,
  val isBookmarked: Boolean = false,
  val heroType: String = "AVENGERS", // "IRON_MAN", "CAPTAIN_AMERICA", "THOR", "DOCTOR_STRANGE", "SPIDER_MAN", "SCIENTIST"
  val accentColorHex: Long = 0xFFFFD700
)

enum class ExamTypeMode(val label: String, val totalMarks: Int, val shortCode: String) {
  JEE_MAIN("JEE Main 2026", 300, "MAINS"),
  JEE_ADVANCED("JEE Advanced 2026", 360, "ADV")
}

enum class ShiftDifficulty(val label: String, val description: String, val percentileMultiplier: Double) {
  TOUGH("Tough Shift", "Lower score needed for 99%ile (~175-185M)", 1.07),
  MODERATE("Moderate Shift", "Standard normal distribution (~195-205M for 99%ile)", 1.00),
  EASY("Easy Shift", "Higher scoring paper (~225-235M for 99%ile)", 0.93)
}

enum class CandidateCategory(val label: String, val shortCode: String, val rankFactor: Double) {
  OPEN_CRL("General (Open / CRL)", "GEN", 1.0),
  GEN_EWS("General-EWS", "EWS", 0.16),
  OBC_NCL("OBC-NCL", "OBC", 0.28),
  SC("Scheduled Caste (SC)", "SC", 0.08),
  ST("Scheduled Tribe (ST)", "ST", 0.04)
}

enum class ScoreEntryMode(val label: String) {
  QUICK_SLIDERS("Direct Mark Sliders"),
  QUESTION_BREAKDOWN("Question Marking (+4 / -1)")
}

data class SubjectQuestionBreakdown(
  val correctCount: Int = 20, // +4 each
  val incorrectCount: Int = 2, // -1 each
  val unattemptedCount: Int = 3 // 0
) {
  val totalQuestions: Int get() = correctCount + incorrectCount + unattemptedCount
  val calculatedMarks: Int get() = ((correctCount * 4) - (incorrectCount * 1)).coerceIn(0, 100)
  val accuracyPercentage: Double get() = if (correctCount + incorrectCount == 0) 0.0 else (correctCount.toDouble() / (correctCount + incorrectCount) * 100.0)
}

data class TargetGoalPreset(
  val id: String,
  val title: String,
  val collegeTarget: String,
  val targetAir: Int,
  val targetScoreMain: Int,
  val targetScoreAdv: Int,
  val iconEmoji: String
)

data class WhatIfScenario(
  val id: String,
  val title: String,
  val description: String,
  val deltaPhysics: Int,
  val deltaChemistry: Int,
  val deltaMath: Int,
  val iconEmoji: String,
  val estimatedHoursNeeded: Int
)

enum class ReminderType(val title: String, val subtitle: String, val iconEmoji: String, val defaultHeader: String) {
  AVENGERS_BATTLECRY("Avengers Protocol", "Inspirational quotes from Tony Stark, Cap & scientists", "🛡️", "Avengers Protocol: Time to Assemble!"),
  FORMULA_BURST("Formula Flash", "Quick morning formula revision alert", "⚡️", "Daily Formula Sprint: Master 5 Equations!"),
  STREAK_SHIELD("Streak Shield", "Evening reminder to protect your study streak", "🔥", "Defend Your Daily Streak: 45m Study Drill"),
  MOCK_TEST_DRILL("Mock Test Call", "Timed PYQ solving and speed test reminder", "📝", "Mock Drill Alert: 30 Questions in 45 Mins")
}

data class NotificationSettings(
  val enabled: Boolean = true,
  val reminderHour: Int = 19, // 7:00 PM
  val reminderMinute: Int = 0,
  val reminderType: ReminderType = ReminderType.AVENGERS_BATTLECRY,
  val soundAndVibration: Boolean = true
) {
  val formattedTime: String
    get() {
      val period = if (reminderHour >= 12) "PM" else "AM"
      val displayHour = when {
        reminderHour == 0 -> 12
        reminderHour > 12 -> reminderHour - 12
        else -> reminderHour
      }
      val displayMin = reminderMinute.toString().padStart(2, '0')
      return "$displayHour:$displayMin $period"
    }
}

data class SyllabusProgressPoint(
  val weekLabel: String,
  val physicsPercent: Float,
  val chemistryPercent: Float,
  val mathPercent: Float,
  val overallPercent: Float,
  val targetPacePercent: Float,
  val chaptersMasteredCumulative: Int,
  val pyqsSolvedCumulative: Int
)

data class PracticeTestScorePoint(
  val id: String,
  val testName: String,
  val dateLabel: String,
  val examType: String, // "JEE Main" or "JEE Advanced"
  val maxMarks: Int, // 300 or 360
  val totalScore: Int,
  val physicsMarks: Int,
  val chemistryMarks: Int,
  val mathMarks: Int,
  val percentile: Double,
  val projectedAir: Int,
  val accuracyPercent: Double,
  val timeTakenMinutes: Int = 180
)

enum class AnalyticsMetricView(val label: String, val shortLabel: String) {
  TOTAL_SCORE("Total Score", "Score (M)"),
  PERCENTILE("Percentile", "Percentile (%)"),
  SUBJECT_SPLIT("Subject Breakdown", "P/C/M Marks"),
  ACCURACY("Accuracy & Speed", "Accuracy (%)")
}

enum class AnalyticsTimeRange(val label: String) {
  LAST_4_WEEKS("Last 4 Weeks"),
  LAST_3_MONTHS("Last 3 Months"),
  ALL_TIME("Full Prep Journey")
}


