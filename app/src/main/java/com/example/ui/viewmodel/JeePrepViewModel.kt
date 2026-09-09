package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CandidateCategory
import com.example.data.Chapter
import com.example.data.ChapterStatus
import com.example.data.ChatMessage
import com.example.data.College
import com.example.data.DailyGoal
import com.example.data.ExamTypeMode
import com.example.data.FormulaCard
import com.example.data.Mentor
import com.example.data.MockTest
import com.example.data.MotivationalQuote
import com.example.data.OfflineCacheInfo
import com.example.data.QuoteCategory
import com.example.data.SampleData
import com.example.data.SavedFormulaSheet
import com.example.data.ScoreEntryMode
import com.example.data.ShiftDifficulty
import com.example.data.SubjectQuestionBreakdown
import com.example.data.SubjectType
import com.example.data.SyllabusTopic
import com.example.data.SyllabusViewMode
import com.example.data.TargetGoalPreset
import com.example.data.TopicFilterMode
import com.example.data.UserProfile
import com.example.data.WeightageLevel
import com.example.data.WhatIfScenario
import com.example.data.local.JeeAppDatabase
import com.example.data.repository.JeeOfflineRepository
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class PomodoroState {
  IDLE, RUNNING, PAUSED, BREAK
}

data class FormulaQuizQuestion(
  val formulaCard: FormulaCard,
  val questionText: String,
  val options: List<String>,
  val correctOptionIndex: Int,
  val selectedOptionIndex: Int? = null,
  val isAnswered: Boolean = false
)

data class JeePrepUiState(
  val themeMode: AppThemeMode = AppThemeMode.OLED_BLACK,
  val userProfile: UserProfile = UserProfile(),
  val chapters: List<Chapter> = SampleData.chapters,
  val dailyGoals: List<DailyGoal> = SampleData.dailyGoals,
  val mockTests: List<MockTest> = SampleData.mockTests,
  val formulas: List<FormulaCard> = SampleData.formulas,
  val savedFormulaSheets: List<SavedFormulaSheet> = SampleData.savedFormulaSheets,
  val colleges: List<College> = SampleData.colleges,
  val mentors: List<Mentor> = SampleData.mentors,
  val chatMessages: Map<String, List<ChatMessage>> = SampleData.sampleChatMessages,
  val offlineCacheInfo: OfflineCacheInfo = OfflineCacheInfo(
    isDbReady = true,
    totalChaptersCached = SampleData.chapters.size,
    totalTopicsCached = SampleData.chapters.sumOf { it.topics.size },
    totalFormulasCached = SampleData.formulas.size,
    totalSheetsCached = SampleData.savedFormulaSheets.size
  ),

  // Selected sub-views / modals
  val selectedSubjectTab: SubjectType = SubjectType.PHYSICS,
  val chapterSearchQuery: String = "",
  val chapterClassFilter: Int? = null, // null for all, 11, or 12
  val chapterWeightageFilter: WeightageLevel? = null,
  val topicFilterMode: TopicFilterMode = TopicFilterMode.ALL,
  val syllabusViewMode: SyllabusViewMode = SyllabusViewMode.CHAPTER_VIEW,
  val expandedChapterIds: Set<String> = emptySet(),
  val formulaSearchQuery: String = "",
  val formulaSubjectFilter: SubjectType? = null,
  val formulaOnlyBookmarked: Boolean = false,
  val selectedFormulaTab: Int = 0, // 0 = All Formulas, 1 = Saved Formula Sheets (Offline)
  val selectedFormulaSheet: SavedFormulaSheet? = null,
  
  // Timer & Pomodoro State
  val timerSecondsRemaining: Int = 25 * 60,
  val timerTotalSeconds: Int = 25 * 60,
  val pomodoroState: PomodoroState = PomodoroState.IDLE,
  val timerSubject: SubjectType = SubjectType.PHYSICS,
  val timerChapterName: String = "Rotational Dynamics",
  val todayStudiedMinutes: Int = 215, // 3h 35m default
  val weeklyStudyHours: List<Float> = listOf(5.5f, 6.2f, 7.0f, 6.8f, 7.5f, 8.2f, 4.5f), // Mon-Sun

  // Rank Predictor & Score Simulator
  val examMode: ExamTypeMode = ExamTypeMode.JEE_MAIN,
  val shiftDifficulty: ShiftDifficulty = ShiftDifficulty.MODERATE,
  val candidateCategory: CandidateCategory = CandidateCategory.OPEN_CRL,
  val scoreEntryMode: ScoreEntryMode = ScoreEntryMode.QUICK_SLIDERS,
  val scorePhysics: Int = 78,
  val scoreChemistry: Int = 85,
  val scoreMath: Int = 72,
  val physicsBreakdown: SubjectQuestionBreakdown = SubjectQuestionBreakdown(correctCount = 20, incorrectCount = 2, unattemptedCount = 3),
  val chemistryBreakdown: SubjectQuestionBreakdown = SubjectQuestionBreakdown(correctCount = 22, incorrectCount = 3, unattemptedCount = 0),
  val mathBreakdown: SubjectQuestionBreakdown = SubjectQuestionBreakdown(correctCount = 18, incorrectCount = 0, unattemptedCount = 7),
  val simDeltaPhysics: Int = 10,
  val simDeltaChemistry: Int = 10,
  val simDeltaMath: Int = 12,
  val activeWhatIfScenarioId: String? = null,
  val selectedTargetPresetId: String? = "preset_top_iits",

  // Formula Quiz
  val activeQuiz: List<FormulaQuizQuestion> = emptyList(),
  val quizScore: Int = 0,
  val isQuizActive: Boolean = false,

  // Selected items for detail modals
  val selectedCollegeForDetail: College? = null,
  val compareCollege1: College? = null,
  val compareCollege2: College? = null,
  val selectedMentorForDetail: Mentor? = null,
  val activeChatMentor: Mentor? = null,

  // Motivation & Avengers Quotes State
  val motivationalQuotes: List<MotivationalQuote> = SampleData.motivationalQuotes,
  val currentQuoteIndex: Int = 0,
  val selectedQuoteCategory: QuoteCategory = QuoteCategory.ALL,
  val isAvengersModeActive: Boolean = true,
  val motivationPulseCount: Int = 0,

  // Local Notification & Daily Reminder Settings
  val notificationSettings: com.example.data.NotificationSettings = com.example.data.NotificationSettings(),
  val lastNotificationSentMessage: String? = null,

  // Visual Analytics & Recharts-style Progress Dashboard
  val syllabusProgressHistory: List<com.example.data.SyllabusProgressPoint> = com.example.data.SampleData.syllabusProgressHistory,
  val practiceTestScores: List<com.example.data.PracticeTestScorePoint> = com.example.data.SampleData.practiceTestScoreHistory,
  val selectedAnalyticsMetric: com.example.data.AnalyticsMetricView = com.example.data.AnalyticsMetricView.TOTAL_SCORE,
  val selectedAnalyticsTimeRange: com.example.data.AnalyticsTimeRange = com.example.data.AnalyticsTimeRange.ALL_TIME,
  val selectedExamTypeFilter: String = "ALL", // "ALL", "JEE Main", "JEE Advanced"
  val selectedScrubbedTestId: String? = "pt12"
) {
  val activeQuote: MotivationalQuote
    get() {
      val filtered = if (selectedQuoteCategory == QuoteCategory.ALL) motivationalQuotes else motivationalQuotes.filter { it.category == selectedQuoteCategory }
      if (filtered.isEmpty()) return motivationalQuotes.first()
      return filtered[currentQuoteIndex.coerceIn(0, filtered.lastIndex)]
    }

  val effectivePhysicsScore: Int
    get() = if (scoreEntryMode == ScoreEntryMode.QUESTION_BREAKDOWN) physicsBreakdown.calculatedMarks else scorePhysics

  val effectiveChemistryScore: Int
    get() = if (scoreEntryMode == ScoreEntryMode.QUESTION_BREAKDOWN) chemistryBreakdown.calculatedMarks else scoreChemistry

  val effectiveMathScore: Int
    get() = if (scoreEntryMode == ScoreEntryMode.QUESTION_BREAKDOWN) mathBreakdown.calculatedMarks else scoreMath

  val maxPossibleScore: Int
    get() = examMode.totalMarks

  val totalScore: Int
    get() = (effectivePhysicsScore + effectiveChemistryScore + effectiveMathScore).coerceIn(0, maxPossibleScore)
  
  val predictedPercentile: Double
    get() = calculatePercentile(totalScore, shiftDifficulty, examMode)

  val predictedAir: Int
    get() = calculateAir(predictedPercentile, examMode)

  val predictedCategoryRank: Int
    get() = (predictedAir * candidateCategory.rankFactor).toInt().coerceAtLeast(1)

  val advQualifyCutoffScore: Int
    get() = when (candidateCategory) {
      CandidateCategory.OPEN_CRL -> 92
      CandidateCategory.GEN_EWS -> 78
      CandidateCategory.OBC_NCL -> 75
      CandidateCategory.SC -> 52
      CandidateCategory.ST -> 46
    }

  val isAdvQualified: Boolean
    get() = totalScore >= advQualifyCutoffScore

  val simTotalScore: Int
    get() = (effectivePhysicsScore + simDeltaPhysics + effectiveChemistryScore + simDeltaChemistry + effectiveMathScore + simDeltaMath).coerceIn(0, maxPossibleScore)

  val simPredictedPercentile: Double
    get() = calculatePercentile(simTotalScore, shiftDifficulty, examMode)

  val simPredictedAir: Int
    get() = calculateAir(simPredictedPercentile, examMode)

  val simPredictedCategoryRank: Int
    get() = (simPredictedAir * candidateCategory.rankFactor).toInt().coerceAtLeast(1)

  val rankImprovement: Int
    get() = (predictedAir - simPredictedAir).coerceAtLeast(0)

  val scoreImprovement: Int
    get() = (simTotalScore - totalScore).coerceAtLeast(0)

  // Subject completion percentages based on checklist topics
  val physicsProgress: Float get() {
    val list = chapters.filter { it.subject == SubjectType.PHYSICS }
    val allTopics = list.flatMap { it.topics }
    if (allTopics.isNotEmpty()) {
      return allTopics.count { it.isCompleted }.toFloat() / allTopics.size.toFloat()
    }
    if (list.isEmpty()) return 0f
    val mastered = list.count { it.status == ChapterStatus.MASTERED || it.status == ChapterStatus.REVISED }
    return mastered.toFloat() / list.size.toFloat()
  }

  val chemistryProgress: Float get() {
    val list = chapters.filter { it.subject == SubjectType.CHEMISTRY }
    val allTopics = list.flatMap { it.topics }
    if (allTopics.isNotEmpty()) {
      return allTopics.count { it.isCompleted }.toFloat() / allTopics.size.toFloat()
    }
    if (list.isEmpty()) return 0f
    val mastered = list.count { it.status == ChapterStatus.MASTERED || it.status == ChapterStatus.REVISED }
    return mastered.toFloat() / list.size.toFloat()
  }

  val mathProgress: Float get() {
    val list = chapters.filter { it.subject == SubjectType.MATHEMATICS }
    val allTopics = list.flatMap { it.topics }
    if (allTopics.isNotEmpty()) {
      return allTopics.count { it.isCompleted }.toFloat() / allTopics.size.toFloat()
    }
    if (list.isEmpty()) return 0f
    val mastered = list.count { it.status == ChapterStatus.MASTERED || it.status == ChapterStatus.REVISED }
    return mastered.toFloat() / list.size.toFloat()
  }

  val overallSyllabusProgress: Float get() = (physicsProgress + chemistryProgress + mathProgress) / 3f

  companion object {
    fun calculatePercentile(
      score: Int,
      shift: ShiftDifficulty = ShiftDifficulty.MODERATE,
      examMode: ExamTypeMode = ExamTypeMode.JEE_MAIN
    ): Double {
      if (examMode == ExamTypeMode.JEE_ADVANCED) {
        // JEE Advanced 360 marks distribution
        val pct = when {
          score >= 320 -> 99.99
          score >= 280 -> 99.85
          score >= 240 -> 99.40
          score >= 200 -> 98.50
          score >= 170 -> 96.80
          score >= 140 -> 93.50
          score >= 110 -> 87.00
          score >= 90 -> 78.00
          score >= 70 -> 65.00
          else -> (score * 0.8).coerceAtLeast(5.0)
        }
        return (pct * shift.percentileMultiplier).coerceIn(5.0, 99.99)
      }

      // JEE Main 300 marks distribution adjusted by shift difficulty
      val rawPercentile = when {
        score >= 285 -> 99.98
        score >= 270 -> 99.92
        score >= 250 -> 99.65
        score >= 230 -> 99.28
        score >= 210 -> 98.85
        score >= 190 -> 98.15
        score >= 170 -> 97.20
        score >= 150 -> 95.80
        score >= 130 -> 93.50
        score >= 110 -> 89.90
        score >= 90 -> 84.50
        score >= 70 -> 76.00
        score >= 50 -> 64.00
        else -> (score * 1.2).coerceAtLeast(10.0)
      }

      // Shift normalization:
      val normalized = if (rawPercentile >= 99.9) {
        rawPercentile
      } else {
        (rawPercentile * shift.percentileMultiplier).coerceIn(10.0, 99.99)
      }
      return String.format(Locale.US, "%.2f", normalized).toDoubleOrNull() ?: normalized
    }

    fun calculateAir(percentile: Double, examMode: ExamTypeMode = ExamTypeMode.JEE_MAIN): Int {
      val totalCandidates = if (examMode == ExamTypeMode.JEE_ADVANCED) 180000.0 else 1400000.0
      val rank = ((100.0 - percentile) / 100.0 * totalCandidates).toInt().coerceAtLeast(1)
      return rank
    }
  }
}

class JeePrepViewModel @JvmOverloads constructor(
  application: Application? = null,
  customRepository: JeeOfflineRepository? = null
) : ViewModel() {

  private val repository: JeeOfflineRepository? = customRepository ?: application?.let {
    JeeOfflineRepository(JeeAppDatabase.getInstance(it))
  }

  private val _uiState = MutableStateFlow(JeePrepUiState())
  val uiState: StateFlow<JeePrepUiState> = _uiState.asStateFlow()

  private var timerJob: Job? = null

  init {
    repository?.let { repo ->
      viewModelScope.launch {
        repo.initializeDatabaseIfEmpty()
      }
      viewModelScope.launch {
        repo.chaptersFlow.collect { dbChapters ->
          if (dbChapters.isNotEmpty()) {
            _uiState.update { state ->
              state.copy(
                chapters = dbChapters,
                offlineCacheInfo = state.offlineCacheInfo.copy(
                  totalChaptersCached = dbChapters.size,
                  totalTopicsCached = dbChapters.sumOf { it.topics.size },
                  isDbReady = true
                )
              )
            }
          }
        }
      }
      viewModelScope.launch {
        repo.formulasFlow.collect { dbFormulas ->
          if (dbFormulas.isNotEmpty()) {
            _uiState.update { state ->
              state.copy(
                formulas = dbFormulas,
                offlineCacheInfo = state.offlineCacheInfo.copy(
                  totalFormulasCached = dbFormulas.size
                )
              )
            }
          }
        }
      }
      viewModelScope.launch {
        repo.formulaSheetsFlow.collect { dbSheets ->
          if (dbSheets.isNotEmpty()) {
            _uiState.update { state ->
              state.copy(
                savedFormulaSheets = dbSheets,
                offlineCacheInfo = state.offlineCacheInfo.copy(
                  totalSheetsCached = dbSheets.count { it.isDownloadedOffline }
                )
              )
            }
          }
        }
      }
    }
  }

  // ---------------- Theme & Profile ----------------
  fun setThemeMode(mode: AppThemeMode) {
    _uiState.update { it.copy(themeMode = mode) }
  }

  fun updateTargetYear(year: Int) {
    _uiState.update { it.copy(userProfile = it.userProfile.copy(targetYear = year)) }
  }

  fun updateTargetScore(score: Int) {
    _uiState.update { it.copy(userProfile = it.userProfile.copy(targetScoreMains = score)) }
  }

  fun updateTargetAir(air: Int) {
    _uiState.update { it.copy(userProfile = it.userProfile.copy(targetAir = air)) }
  }

  fun updateProfile(
    name: String,
    targetYear: Int,
    targetAir: Int,
    dreamCollege: String,
    dreamBranch: String,
    classLevel: String,
    category: String
  ) {
    _uiState.update {
      it.copy(
        userProfile = it.userProfile.copy(
          name = name,
          targetYear = targetYear,
          targetAir = targetAir,
          dreamCollege = dreamCollege,
          dreamBranch = dreamBranch,
          classLevel = classLevel,
          category = category
        )
      )
    }
  }

  // ---------------- Subject & Syllabus Management ----------------
  fun setSelectedSubjectTab(subject: SubjectType) {
    _uiState.update { it.copy(selectedSubjectTab = subject) }
  }

  fun setChapterSearchQuery(query: String) {
    _uiState.update { it.copy(chapterSearchQuery = query) }
  }

  fun setChapterClassFilter(classLevel: Int?) {
    _uiState.update { it.copy(chapterClassFilter = classLevel) }
  }

  fun setChapterWeightageFilter(weightage: WeightageLevel?) {
    _uiState.update { it.copy(chapterWeightageFilter = weightage) }
  }

  fun setTopicFilterMode(mode: TopicFilterMode) {
    _uiState.update { it.copy(topicFilterMode = mode) }
  }

  fun setSyllabusViewMode(mode: SyllabusViewMode) {
    _uiState.update { it.copy(syllabusViewMode = mode) }
  }

  fun toggleChapterExpanded(chapterId: String) {
    _uiState.update { state ->
      val newExpanded = if (state.expandedChapterIds.contains(chapterId)) {
        state.expandedChapterIds - chapterId
      } else {
        state.expandedChapterIds + chapterId
      }
      state.copy(expandedChapterIds = newExpanded)
    }
  }

  fun expandAllChapters(expand: Boolean) {
    _uiState.update { state ->
      val newExpanded = if (expand) {
        state.chapters.filter { it.subject == state.selectedSubjectTab }.map { it.id }.toSet()
      } else {
        emptySet()
      }
      state.copy(expandedChapterIds = newExpanded)
    }
  }

  fun toggleTopicCompletion(chapterId: String, topicId: String) {
    var newCompleted = false
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.id == chapterId) {
          val updatedTopics = ch.topics.map { top ->
            if (top.id == topicId) {
              newCompleted = !top.isCompleted
              top.copy(isCompleted = !top.isCompleted)
            } else top
          }
          val allDone = updatedTopics.isNotEmpty() && updatedTopics.all { it.isCompleted }
          val anyDone = updatedTopics.any { it.isCompleted }
          val newStatus = when {
            allDone -> ChapterStatus.MASTERED
            anyDone -> if (ch.status == ChapterStatus.MASTERED) ChapterStatus.REVISED else ChapterStatus.IN_PROGRESS
            else -> ChapterStatus.NOT_STARTED
          }
          ch.copy(topics = updatedTopics, status = newStatus)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.toggleTopicCompletion(chapterId, topicId, newCompleted)
    }
  }

  fun markAllTopicsInChapter(chapterId: String, completed: Boolean) {
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.id == chapterId) {
          val updatedTopics = ch.topics.map { it.copy(isCompleted = completed) }
          val newStatus = if (completed) ChapterStatus.MASTERED else ChapterStatus.NOT_STARTED
          ch.copy(topics = updatedTopics, status = newStatus)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.markAllTopicsInChapter(chapterId, completed)
    }
  }

  fun markAllSubjectTopics(subject: SubjectType, completed: Boolean) {
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.subject == subject) {
          val updatedTopics = ch.topics.map { it.copy(isCompleted = completed) }
          val newStatus = if (completed) ChapterStatus.MASTERED else ChapterStatus.NOT_STARTED
          ch.copy(topics = updatedTopics, status = newStatus)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.markAllSubjectTopics(subject, completed)
    }
  }

  fun addCustomTopic(chapterId: String, topicName: String, isHighYield: Boolean, keyFormulaHint: String?) {
    if (topicName.isBlank()) return
    val newTopic = SyllabusTopic(
      id = "custom_${System.currentTimeMillis()}",
      name = topicName.trim(),
      isCompleted = false,
      isHighYield = isHighYield,
      tag = if (isHighYield) "High Yield" else "Custom",
      keyFormulaHint = keyFormulaHint?.takeIf { it.isNotBlank() }
    )
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.id == chapterId) {
          ch.copy(topics = ch.topics + newTopic)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.addCustomTopic(chapterId, newTopic)
    }
  }

  fun cycleChapterStatus(chapterId: String) {
    var nextStatus = ChapterStatus.NOT_STARTED
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.id == chapterId) {
          nextStatus = when (ch.status) {
            ChapterStatus.NOT_STARTED -> ChapterStatus.IN_PROGRESS
            ChapterStatus.IN_PROGRESS -> ChapterStatus.REVISED
            ChapterStatus.REVISED -> ChapterStatus.MASTERED
            ChapterStatus.MASTERED -> ChapterStatus.NOT_STARTED
          }
          val updatedTopics = if (nextStatus == ChapterStatus.MASTERED) {
            ch.topics.map { it.copy(isCompleted = true) }
          } else if (nextStatus == ChapterStatus.NOT_STARTED) {
            ch.topics.map { it.copy(isCompleted = false) }
          } else {
            ch.topics
          }
          ch.copy(status = nextStatus, revisionCount = if (nextStatus == ChapterStatus.MASTERED) ch.revisionCount + 1 else ch.revisionCount, topics = updatedTopics)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.updateChapterStatus(chapterId, nextStatus)
    }
  }

  fun incrementChapterPyqs(chapterId: String, delta: Int = 5) {
    var newCount = 0
    var newStatus = ChapterStatus.NOT_STARTED
    _uiState.update { state ->
      val updated = state.chapters.map { ch ->
        if (ch.id == chapterId) {
          newCount = (ch.pyqsSolved + delta).coerceIn(0, ch.totalPyqs)
          newStatus = if (newCount >= ch.totalPyqs && ch.status != ChapterStatus.MASTERED) ChapterStatus.MASTERED else ch.status
          ch.copy(pyqsSolved = newCount, status = newStatus)
        } else ch
      }
      state.copy(chapters = updated)
    }
    viewModelScope.launch {
      repository?.updateChapterPyqs(chapterId, newCount, newStatus)
    }
  }

  // ---------------- Daily Goals ----------------
  fun toggleDailyGoal(goalId: String) {
    _uiState.update { state ->
      val updated = state.dailyGoals.map { goal ->
        if (goal.id == goalId) goal.copy(isCompleted = !goal.isCompleted) else goal
      }
      state.copy(dailyGoals = updated)
    }
  }

  fun addDailyGoal(title: String, subject: SubjectType, durationMinutes: Int, isHighPriority: Boolean) {
    val newGoal = DailyGoal(
      id = UUID.randomUUID().toString(),
      title = title,
      subject = subject,
      isCompleted = false,
      durationMinutes = durationMinutes,
      isHighPriority = isHighPriority
    )
    _uiState.update { it.copy(dailyGoals = it.dailyGoals + newGoal) }
  }

  fun deleteDailyGoal(goalId: String) {
    _uiState.update { state ->
      state.copy(dailyGoals = state.dailyGoals.filterNot { it.id == goalId })
    }
  }

  // ---------------- Pomodoro Study Timer ----------------
  fun setTimerDuration(minutes: Int) {
    timerJob?.cancel()
    _uiState.update {
      it.copy(
        timerSecondsRemaining = minutes * 60,
        timerTotalSeconds = minutes * 60,
        pomodoroState = PomodoroState.IDLE
      )
    }
  }

  fun setTimerSubjectAndChapter(subject: SubjectType, chapterName: String) {
    _uiState.update {
      it.copy(timerSubject = subject, timerChapterName = chapterName)
    }
  }

  fun startTimer() {
    if (_uiState.value.pomodoroState == PomodoroState.RUNNING) return
    _uiState.update { it.copy(pomodoroState = PomodoroState.RUNNING) }

    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      while (_uiState.value.timerSecondsRemaining > 0 && _uiState.value.pomodoroState == PomodoroState.RUNNING) {
        delay(1000)
        _uiState.update { state ->
          val nextSecs = state.timerSecondsRemaining - 1
          if (nextSecs <= 0) {
            val minutesLogged = state.timerTotalSeconds / 60
            state.copy(
              timerSecondsRemaining = 0,
              pomodoroState = PomodoroState.BREAK,
              todayStudiedMinutes = state.todayStudiedMinutes + minutesLogged
            )
          } else {
            state.copy(timerSecondsRemaining = nextSecs)
          }
        }
      }
    }
  }

  fun pauseTimer() {
    timerJob?.cancel()
    _uiState.update { it.copy(pomodoroState = PomodoroState.PAUSED) }
  }

  fun resetTimer() {
    timerJob?.cancel()
    _uiState.update {
      it.copy(
        timerSecondsRemaining = it.timerTotalSeconds,
        pomodoroState = PomodoroState.IDLE
      )
    }
  }

  // ---------------- Rank Predictor & Simulator ----------------
  fun setExamTypeMode(mode: ExamTypeMode) {
    _uiState.update {
      it.copy(
        examMode = mode,
        scorePhysics = if (mode == ExamTypeMode.JEE_ADVANCED) 85 else 78,
        scoreChemistry = if (mode == ExamTypeMode.JEE_ADVANCED) 92 else 85,
        scoreMath = if (mode == ExamTypeMode.JEE_ADVANCED) 75 else 72
      )
    }
  }

  fun setShiftDifficulty(shift: ShiftDifficulty) {
    _uiState.update { it.copy(shiftDifficulty = shift) }
  }

  fun setCandidateCategory(category: CandidateCategory) {
    _uiState.update { it.copy(candidateCategory = category) }
  }

  fun setScoreEntryMode(mode: ScoreEntryMode) {
    _uiState.update { it.copy(scoreEntryMode = mode) }
  }

  fun updateQuestionBreakdown(subject: SubjectType, correct: Int, incorrect: Int, unattempted: Int) {
    val breakdown = SubjectQuestionBreakdown(
      correctCount = correct.coerceIn(0, 25),
      incorrectCount = incorrect.coerceIn(0, 25 - correct),
      unattemptedCount = unattempted.coerceIn(0, 25)
    )
    _uiState.update { state ->
      when (subject) {
        SubjectType.PHYSICS -> state.copy(
          physicsBreakdown = breakdown,
          scorePhysics = breakdown.calculatedMarks
        )
        SubjectType.CHEMISTRY -> state.copy(
          chemistryBreakdown = breakdown,
          scoreChemistry = breakdown.calculatedMarks
        )
        SubjectType.MATHEMATICS -> state.copy(
          mathBreakdown = breakdown,
          scoreMath = breakdown.calculatedMarks
        )
      }
    }
  }

  fun updateSubjectScores(physics: Int, chemistry: Int, math: Int) {
    val maxPerSubject = if (_uiState.value.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100
    _uiState.update {
      it.copy(
        scorePhysics = physics.coerceIn(0, maxPerSubject),
        scoreChemistry = chemistry.coerceIn(0, maxPerSubject),
        scoreMath = math.coerceIn(0, maxPerSubject)
      )
    }
  }

  fun updateSimDeltas(deltaP: Int, deltaC: Int, deltaM: Int) {
    val maxPerSubject = if (_uiState.value.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100
    _uiState.update {
      it.copy(
        simDeltaPhysics = deltaP.coerceIn(0, maxPerSubject - it.effectivePhysicsScore),
        simDeltaChemistry = deltaC.coerceIn(0, maxPerSubject - it.effectiveChemistryScore),
        simDeltaMath = deltaM.coerceIn(0, maxPerSubject - it.effectiveMathScore),
        activeWhatIfScenarioId = null
      )
    }
  }

  fun applyWhatIfScenario(scenario: WhatIfScenario) {
    val maxPerSubject = if (_uiState.value.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100
    _uiState.update { state ->
      val dp = scenario.deltaPhysics.coerceIn(0, maxPerSubject - state.effectivePhysicsScore)
      val dc = scenario.deltaChemistry.coerceIn(0, maxPerSubject - state.effectiveChemistryScore)
      val dm = scenario.deltaMath.coerceIn(0, maxPerSubject - state.effectiveMathScore)
      state.copy(
        simDeltaPhysics = dp,
        simDeltaChemistry = dc,
        simDeltaMath = dm,
        activeWhatIfScenarioId = scenario.id
      )
    }
  }

  fun applyTargetPreset(preset: TargetGoalPreset) {
    _uiState.update { state ->
      val targetScore = if (state.examMode == ExamTypeMode.JEE_ADVANCED) preset.targetScoreAdv else preset.targetScoreMain
      val currentScore = state.totalScore
      val gap = (targetScore - currentScore).coerceAtLeast(0)
      
      // Distribute gap sensibly: Chemistry (+40%), Physics (+35%), Math (+25%)
      val deltaC = (gap * 0.40).toInt()
      val deltaP = (gap * 0.35).toInt()
      val deltaM = (gap - deltaC - deltaP).coerceAtLeast(0)

      val maxPerSubject = if (state.examMode == ExamTypeMode.JEE_ADVANCED) 120 else 100
      state.copy(
        selectedTargetPresetId = preset.id,
        simDeltaPhysics = deltaP.coerceIn(0, maxPerSubject - state.effectivePhysicsScore),
        simDeltaChemistry = deltaC.coerceIn(0, maxPerSubject - state.effectiveChemistryScore),
        simDeltaMath = deltaM.coerceIn(0, maxPerSubject - state.effectiveMathScore)
      )
    }
  }

  fun resetSimDeltas() {
    _uiState.update {
      it.copy(
        simDeltaPhysics = 0,
        simDeltaChemistry = 0,
        simDeltaMath = 0,
        activeWhatIfScenarioId = null
      )
    }
  }

  // ---------------- Formulas Hub & Bookmarks ----------------
  fun setFormulaSearchQuery(query: String) {
    _uiState.update { it.copy(formulaSearchQuery = query) }
  }

  fun setFormulaSubjectFilter(subject: SubjectType?) {
    _uiState.update { it.copy(formulaSubjectFilter = subject) }
  }

  fun toggleFormulaBookmark(formulaId: String) {
    var newBookmarked = false
    _uiState.update { state ->
      val updated = state.formulas.map { f ->
        if (f.id == formulaId) {
          newBookmarked = !f.isBookmarked
          f.copy(isBookmarked = newBookmarked)
        } else f
      }
      state.copy(formulas = updated)
    }
    viewModelScope.launch {
      repository?.toggleFormulaBookmark(formulaId, newBookmarked)
    }
  }

  fun toggleFormulaOffline(formulaId: String) {
    var newOffline = true
    _uiState.update { state ->
      val updated = state.formulas.map { f ->
        if (f.id == formulaId) {
          newOffline = !f.isSavedOffline
          f.copy(isSavedOffline = newOffline)
        } else f
      }
      state.copy(formulas = updated)
    }
    viewModelScope.launch {
      repository?.toggleFormulaOffline(formulaId, newOffline)
    }
  }

  fun toggleSheetOffline(sheetId: String) {
    var newOffline = true
    _uiState.update { state ->
      val updated = state.savedFormulaSheets.map { sheet ->
        if (sheet.id == sheetId) {
          newOffline = !sheet.isDownloadedOffline
          sheet.copy(isDownloadedOffline = newOffline)
        } else sheet
      }
      state.copy(
        savedFormulaSheets = updated,
        offlineCacheInfo = state.offlineCacheInfo.copy(
          totalSheetsCached = updated.count { it.isDownloadedOffline }
        )
      )
    }
    viewModelScope.launch {
      repository?.toggleSheetOffline(sheetId, newOffline)
    }
  }

  fun setSelectedFormulaTab(tabIndex: Int) {
    _uiState.update { it.copy(selectedFormulaTab = tabIndex) }
  }

  fun setSelectedFormulaSheet(sheet: SavedFormulaSheet?) {
    _uiState.update { it.copy(selectedFormulaSheet = sheet) }
  }

  fun toggleFormulaOnlyBookmarked() {
    _uiState.update { it.copy(formulaOnlyBookmarked = !it.formulaOnlyBookmarked) }
  }

  fun startFormulaQuiz() {
    val quizItems = _uiState.value.formulas.shuffled().take(5).map { card ->
      val wrongFormulas = _uiState.value.formulas
        .filter { it.id != card.id }
        .shuffled()
        .take(3)
        .map { it.formula }
      val allOptions = (wrongFormulas + card.formula).shuffled()
      val correctIdx = allOptions.indexOf(card.formula)
      FormulaQuizQuestion(
        formulaCard = card,
        questionText = "What is the correct mathematical formula for: ${card.title} (${card.topic})?",
        options = allOptions,
        correctOptionIndex = correctIdx
      )
    }
    _uiState.update {
      it.copy(
        activeQuiz = quizItems,
        quizScore = 0,
        isQuizActive = true
      )
    }
  }

  fun answerQuizQuestion(questionIndex: Int, selectedOptionIndex: Int) {
    _uiState.update { state ->
      if (questionIndex >= state.activeQuiz.size) return@update state
      val q = state.activeQuiz[questionIndex]
      if (q.isAnswered) return@update state
      val isCorrect = selectedOptionIndex == q.correctOptionIndex
      val updatedQuestions = state.activeQuiz.toMutableList()
      updatedQuestions[questionIndex] = q.copy(
        selectedOptionIndex = selectedOptionIndex,
        isAnswered = true
      )
      state.copy(
        activeQuiz = updatedQuestions,
        quizScore = if (isCorrect) state.quizScore + 1 else state.quizScore
      )
    }
  }

  fun exitQuiz() {
    _uiState.update { it.copy(isQuizActive = false) }
  }

  // ---------------- Colleges & Comparison ----------------
  fun setSelectedCollegeForDetail(college: College?) {
    _uiState.update { it.copy(selectedCollegeForDetail = college) }
  }

  fun setCompareColleges(c1: College?, c2: College?) {
    _uiState.update { it.copy(compareCollege1 = c1, compareCollege2 = c2) }
  }

  // ---------------- Mentors & Live Chat Simulation ----------------
  fun setSelectedMentorForDetail(mentor: Mentor?) {
    _uiState.update { it.copy(selectedMentorForDetail = mentor) }
  }

  fun setActiveChatMentor(mentor: Mentor?) {
    _uiState.update { it.copy(activeChatMentor = mentor) }
  }

  fun sendChatMessage(mentorId: String, userText: String) {
    if (userText.isBlank()) return
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentTime = sdf.format(Date())
    val userMsg = ChatMessage(
      id = UUID.randomUUID().toString(),
      mentorId = mentorId,
      text = userText.trim(),
      isFromUser = true,
      timestamp = currentTime
    )

    _uiState.update { state ->
      val currentList = state.chatMessages[mentorId] ?: emptyList()
      state.copy(
        chatMessages = state.chatMessages + (mentorId to (currentList + userMsg))
      )
    }

    // Generate smart context-aware mentor response after 1 second
    viewModelScope.launch {
      delay(1200)
      val mentor = _uiState.value.mentors.find { it.id == mentorId }
      val replyText = generateMentorReply(mentor, userText)
      val replyTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
      val mentorMsg = ChatMessage(
        id = UUID.randomUUID().toString(),
        mentorId = mentorId,
        text = replyText,
        isFromUser = false,
        timestamp = replyTime
      )

      _uiState.update { state ->
        val currentList = state.chatMessages[mentorId] ?: emptyList()
        state.copy(
          chatMessages = state.chatMessages + (mentorId to (currentList + mentorMsg))
        )
      }
    }
  }

  private fun generateMentorReply(mentor: Mentor?, userQuery: String): String {
    val q = userQuery.lowercase()
    return when {
      q.contains("backlog") || q.contains("11th") ->
        "To clear backlogs effectively: 1) Never stop your ongoing 12th chapters. 2) Allocate a fixed 2-hour evening slot exclusively for high-weightage 11th topics (Rotational, Thermodynamics, GOC, Conics). 3) Complete only JEE Main PYQs first to build confidence quickly!"
      q.contains("mock") || q.contains("score") || q.contains("marks") ->
        "Mock test analysis is where 50% of rank improvement happens! Spend at least 2 hours analyzing every mock: categorize mistakes into 'Calculation error', 'Conceptual gap', and 'Time-pressure mistake'. Re-attempt all incorrect questions without looking at solutions."
      q.contains("math") || q.contains("calculus") || q.contains("integration") ->
        "For JEE Maths: Focus intensely on High-ROI chapters: Vectors & 3D (3-4 questions guaranteed), Matrices & Determinants (2 questions), Sequence & Series, and Definite Integration properties (King's rule & Leibnitz). Master 25 PYQs per topic!"
      q.contains("physics") || q.contains("mechanics") || q.contains("rotation") ->
        "In Physics, always draw the Free Body Diagram (FBD) and establish coordinate axes before writing equations. Master Work-Energy theorem and conservation laws—they eliminate 80% of complex kinematics calculation!"
      q.contains("chem") || q.contains("organic") || q.contains("inorganic") ->
        "Chemistry gives you the best speed-to-marks ratio in the entire exam! NCERT is sufficient for 95% of Inorganic & Biomolecules. For Organic, create a reaction flowchart from Hydrocarbons to Amines and review it daily for 15 minutes."
      q.contains("stress") || q.contains("anxiety") || q.contains("nervous") || q.contains("fear") ->
        "Exam anxiety happens when we focus on the result instead of the daily process. Trust your hard work! Take short 5-minute breathing breaks between study slots, stay hydrated, and remember that consistent daily problem-solving guarantees a great rank."
      else ->
        "Great question! Focus on consistent daily revision and solving recent 5-year PYQs under timed conditions. Let me know if you want me to review your mock breakdown or suggest high-yield topics for your next session!"
    }
  }

  // ---------------- Motivation & Avengers Protocol ----------------
  fun updateUserProfile(
    name: String? = null,
    dreamCollege: String? = null,
    dreamBranch: String? = null,
    targetAir: Int? = null,
    targetYear: Int? = null,
    avatarUri: String? = null,
    avatarPreset: String? = null,
    avatarColorHex: Long? = null,
    clearCustomImage: Boolean = false
  ) {
    _uiState.update { state ->
      val p = state.userProfile
      val updatedAvatarUri = if (clearCustomImage) null else (avatarUri ?: p.avatarUri)
      state.copy(
        userProfile = p.copy(
          name = name?.trim()?.takeIf { it.isNotBlank() } ?: p.name,
          dreamCollege = dreamCollege?.trim()?.takeIf { it.isNotBlank() } ?: p.dreamCollege,
          dreamBranch = dreamBranch?.trim()?.takeIf { it.isNotBlank() } ?: p.dreamBranch,
          targetAir = targetAir ?: p.targetAir,
          targetYear = targetYear ?: p.targetYear,
          avatarUri = updatedAvatarUri,
          avatarPreset = avatarPreset ?: p.avatarPreset,
          avatarColorHex = avatarColorHex ?: p.avatarColorHex
        )
      )
    }
  }

  fun nextMotivationalQuote() {
    _uiState.update { state ->
      val filtered = if (state.selectedQuoteCategory == QuoteCategory.ALL) state.motivationalQuotes else state.motivationalQuotes.filter { it.category == state.selectedQuoteCategory }
      val nextIdx = if (filtered.isEmpty()) 0 else (state.currentQuoteIndex + 1) % filtered.size
      state.copy(currentQuoteIndex = nextIdx)
    }
  }

  fun prevMotivationalQuote() {
    _uiState.update { state ->
      val filtered = if (state.selectedQuoteCategory == QuoteCategory.ALL) state.motivationalQuotes else state.motivationalQuotes.filter { it.category == state.selectedQuoteCategory }
      val prevIdx = if (filtered.isEmpty()) 0 else if (state.currentQuoteIndex - 1 < 0) filtered.lastIndex else state.currentQuoteIndex - 1
      state.copy(currentQuoteIndex = prevIdx)
    }
  }

  fun randomMotivationalQuote() {
    _uiState.update { state ->
      val filtered = if (state.selectedQuoteCategory == QuoteCategory.ALL) state.motivationalQuotes else state.motivationalQuotes.filter { it.category == state.selectedQuoteCategory }
      if (filtered.size <= 1) return@update state
      val randomIdx = (filtered.indices.filter { it != state.currentQuoteIndex }).randomOrNull() ?: 0
      state.copy(
        currentQuoteIndex = randomIdx,
        motivationPulseCount = state.motivationPulseCount + 1
      )
    }
  }

  fun toggleQuoteBookmark(quoteId: String) {
    _uiState.update { state ->
      val updated = state.motivationalQuotes.map { q ->
        if (q.id == quoteId) q.copy(isBookmarked = !q.isBookmarked) else q
      }
      state.copy(motivationalQuotes = updated)
    }
  }

  fun setSelectedQuoteCategory(category: QuoteCategory) {
    _uiState.update { state ->
      state.copy(
        selectedQuoteCategory = category,
        currentQuoteIndex = 0
      )
    }
  }

  fun toggleAvengersMode() {
    _uiState.update { state ->
      state.copy(isAvengersModeActive = !state.isAvengersModeActive)
    }
  }

  fun boostArcReactorEnergy() {
    _uiState.update { state ->
      val newEnergy = (state.userProfile.arcReactorEnergy + 10).coerceAtMost(100)
      state.copy(
        userProfile = state.userProfile.copy(arcReactorEnergy = newEnergy),
        motivationPulseCount = state.motivationPulseCount + 1
      )
    }
  }

  // ---------------- Daily Study Reminder & Notification Management ----------------
  fun setNotificationEnabled(context: android.content.Context, enabled: Boolean) {
    _uiState.update { state ->
      val updated = state.notificationSettings.copy(enabled = enabled)
      state.copy(notificationSettings = updated)
    }
    if (enabled) {
      val s = _uiState.value.notificationSettings
      com.example.notification.NotificationHelper.scheduleDailyAlarm(
        context = context,
        hour = s.reminderHour,
        minute = s.reminderMinute,
        reminderType = s.reminderType
      )
    } else {
      com.example.notification.NotificationHelper.cancelDailyAlarm(context)
    }
  }

  fun setReminderTime(context: android.content.Context, hour: Int, minute: Int) {
    _uiState.update { state ->
      val updated = state.notificationSettings.copy(reminderHour = hour, reminderMinute = minute)
      state.copy(notificationSettings = updated)
    }
    if (_uiState.value.notificationSettings.enabled) {
      com.example.notification.NotificationHelper.scheduleDailyAlarm(
        context = context,
        hour = hour,
        minute = minute,
        reminderType = _uiState.value.notificationSettings.reminderType
      )
    }
  }

  fun setReminderType(context: android.content.Context, type: com.example.data.ReminderType) {
    _uiState.update { state ->
      val updated = state.notificationSettings.copy(reminderType = type)
      state.copy(notificationSettings = updated)
    }
    if (_uiState.value.notificationSettings.enabled) {
      com.example.notification.NotificationHelper.scheduleDailyAlarm(
        context = context,
        hour = _uiState.value.notificationSettings.reminderHour,
        minute = _uiState.value.notificationSettings.reminderMinute,
        reminderType = type
      )
    }
  }

  fun sendTestNotificationNow(context: android.content.Context) {
    val s = _uiState.value.notificationSettings
    com.example.notification.NotificationHelper.sendStudyReminder(
      context = context,
      reminderType = s.reminderType,
      isTest = true
    )
    _uiState.update {
      it.copy(lastNotificationSentMessage = "Test ${s.reminderType.title} notification dispatched to device notification tray!")
    }
  }

  fun clearNotificationStatusMessage() {
    _uiState.update { it.copy(lastNotificationSentMessage = null) }
  }

  // ---------------- Visual Analytics & Recharts-style Chart Functions ----------------
  fun setAnalyticsMetric(metric: com.example.data.AnalyticsMetricView) {
    _uiState.update { it.copy(selectedAnalyticsMetric = metric) }
  }

  fun setAnalyticsTimeRange(range: com.example.data.AnalyticsTimeRange) {
    _uiState.update { it.copy(selectedAnalyticsTimeRange = range) }
  }

  fun setExamTypeFilter(filter: String) {
    _uiState.update { it.copy(selectedExamTypeFilter = filter) }
  }

  fun selectScrubbedTest(testId: String?) {
    _uiState.update { it.copy(selectedScrubbedTestId = testId) }
  }

  fun logNewPracticeTest(
    testName: String,
    examType: String,
    physicsScore: Int,
    chemistryScore: Int,
    mathScore: Int,
    accuracy: Double
  ) {
    val total = physicsScore + chemistryScore + mathScore
    val maxM = if (examType == "JEE Advanced") 360 else 300
    val targetExamMode = if (examType == "JEE Advanced") ExamTypeMode.JEE_ADVANCED else ExamTypeMode.JEE_MAIN
    val perc = JeePrepUiState.calculatePercentile(total, _uiState.value.shiftDifficulty, targetExamMode)
    val air = JeePrepUiState.calculateAir(perc, targetExamMode)
    
    val newPoint = com.example.data.PracticeTestScorePoint(
      id = "pt_${System.currentTimeMillis()}",
      testName = testName,
      dateLabel = "Today",
      examType = examType,
      maxMarks = maxM,
      totalScore = total,
      physicsMarks = physicsScore,
      chemistryMarks = chemistryScore,
      mathMarks = mathScore,
      percentile = perc,
      projectedAir = air,
      accuracyPercent = accuracy
    )

    _uiState.update { state ->
      val updatedList = state.practiceTestScores + newPoint
      state.copy(
        practiceTestScores = updatedList,
        selectedScrubbedTestId = newPoint.id
      )
    }
  }
}


