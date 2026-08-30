package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

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
}
