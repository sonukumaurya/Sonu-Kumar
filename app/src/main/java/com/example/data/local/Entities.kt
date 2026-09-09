package com.example.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "chapters")
data class ChapterEntity(
  @PrimaryKey val id: String,
  val subject: String,
  val name: String,
  val classLevel: Int,
  val weightage: String,
  val pyqsSolved: Int,
  val totalPyqs: Int,
  val status: String,
  val revisionCount: Int,
  val keyFormulaCount: Int,
  val keyConcepts: List<String>,
  val lastModified: Long = System.currentTimeMillis()
)

@Entity(
  tableName = "syllabus_topics",
  indices = [Index("chapterId")]
)
data class SyllabusTopicEntity(
  @PrimaryKey val id: String,
  val chapterId: String,
  val name: String,
  val isCompleted: Boolean,
  val isHighYield: Boolean,
  val tag: String = "Core",
  val keyFormulaHint: String? = null,
  val notes: String = "",
  val orderIndex: Int = 0
)

@Entity(tableName = "formulas")
data class FormulaEntity(
  @PrimaryKey val id: String,
  val subject: String,
  val topic: String,
  val title: String,
  val formula: String,
  val explanation: String,
  val unitsAndConstants: String,
  val isBookmarked: Boolean = false,
  val isSavedOffline: Boolean = true,
  val cachedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "formula_sheets")
data class FormulaSheetEntity(
  @PrimaryKey val id: String,
  val title: String,
  val subject: String,
  val description: String,
  val formulaCount: Int,
  val formulaIds: List<String>,
  val isDownloadedOffline: Boolean = true,
  val category: String = "High-Yield",
  val lastUpdatedFormatted: String = "Cached in Room DB",
  val downloadSizeKb: Int = 32
)
