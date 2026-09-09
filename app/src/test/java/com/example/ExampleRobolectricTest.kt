package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.ChapterStatus
import com.example.data.SubjectType
import com.example.data.WeightageLevel
import com.example.data.local.ChapterEntity
import com.example.data.local.FormulaEntity
import com.example.data.local.FormulaSheetEntity
import com.example.data.local.JeeAppDatabase
import com.example.data.local.SyllabusTopicEntity
import com.example.data.repository.JeeOfflineRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  private lateinit var database: JeeAppDatabase
  private lateinit var repository: JeeOfflineRepository

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    database = Room.inMemoryDatabaseBuilder(context, JeeAppDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    repository = JeeOfflineRepository(database)
  }

  @After
  fun tearDown() {
    database.close()
  }

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("JEE Prep", appName)
  }

  @Test
  fun `verify analytics progress and test score logging`() {
    val vm = com.example.ui.viewmodel.JeePrepViewModel()
    val initialTestsCount = vm.uiState.value.practiceTestScores.size
    
    // Log new mock test
    vm.logNewPracticeTest(
      testName = "All India Mock Test #15",
      examType = "JEE Main",
      physicsScore = 90,
      chemistryScore = 85,
      mathScore = 75,
      accuracy = 92.0
    )

    val updatedState = vm.uiState.value
    assertEquals(initialTestsCount + 1, updatedState.practiceTestScores.size)
    val latest = updatedState.practiceTestScores.last()
    assertEquals(250, latest.totalScore)
    assertEquals("All India Mock Test #15", latest.testName)
    assertEquals(92.0, latest.accuracyPercent, 0.01)
  }

  @Test
  fun `verify room database offline caching for syllabus and formula sheets`() = runBlocking {
    // Seed initial data through repository
    repository.initializeDatabaseIfEmpty()

    // 1. Verify chapters and syllabus topics are cached in Room DB
    val chapters = repository.chaptersFlow.first()
    assertTrue("Chapters should be populated in Room database", chapters.isNotEmpty())
    val firstChapter = chapters.first()
    assertNotNull(firstChapter)
    assertTrue("Chapter should contain syllabus topics", firstChapter.topics.isNotEmpty())

    // 2. Verify formulas and saved sheets are cached in Room DB
    val formulas = repository.formulasFlow.first()
    assertTrue("Formulas should be cached in Room database", formulas.isNotEmpty())

    val sheets = repository.formulaSheetsFlow.first()
    assertTrue("Saved formula sheets should be cached in Room database", sheets.isNotEmpty())

    // 3. Test toggling topic completion in Room DB
    val topicToToggle = firstChapter.topics.first()
    val initialStatus = topicToToggle.isCompleted
    repository.toggleTopicCompletion(firstChapter.id, topicToToggle.id, !initialStatus)

    val updatedChapters = repository.chaptersFlow.first()
    val updatedTopic = updatedChapters.flatMap { it.topics }.first { it.id == topicToToggle.id }
    assertEquals(!initialStatus, updatedTopic.isCompleted)

    // 4. Test toggling saved formula sheet offline status
    val firstSheet = sheets.first()
    repository.toggleSheetOffline(firstSheet.id, false)
    val sheetsAfterToggle = repository.formulaSheetsFlow.first()
    val updatedSheet = sheetsAfterToggle.first { it.id == firstSheet.id }
    assertEquals(false, updatedSheet.isDownloadedOffline)
  }
}

