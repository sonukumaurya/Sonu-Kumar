package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ChapterDao {
  @Query("SELECT * FROM chapters ORDER BY classLevel ASC, id ASC")
  fun getAllChapters(): Flow<List<ChapterEntity>>

  @Query("SELECT * FROM chapters WHERE subject = :subject ORDER BY classLevel ASC, id ASC")
  fun getChaptersBySubject(subject: String): Flow<List<ChapterEntity>>

  @Query("SELECT * FROM chapters WHERE id = :id LIMIT 1")
  suspend fun getChapterById(id: String): ChapterEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertChapters(chapters: List<ChapterEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertChapter(chapter: ChapterEntity)

  @Update
  suspend fun updateChapter(chapter: ChapterEntity)

  @Query("UPDATE chapters SET status = :status, lastModified = :timestamp WHERE id = :chapterId")
  suspend fun updateChapterStatus(chapterId: String, status: String, timestamp: Long = System.currentTimeMillis())

  @Query("UPDATE chapters SET pyqsSolved = :pyqs, status = :status, lastModified = :timestamp WHERE id = :chapterId")
  suspend fun updateChapterPyqs(chapterId: String, pyqs: Int, status: String, timestamp: Long = System.currentTimeMillis())

  @Query("SELECT COUNT(*) FROM chapters")
  suspend fun getChapterCount(): Int
}

@Dao
interface TopicDao {
  @Query("SELECT * FROM syllabus_topics ORDER BY orderIndex ASC")
  fun getAllTopics(): Flow<List<SyllabusTopicEntity>>

  @Query("SELECT * FROM syllabus_topics WHERE chapterId = :chapterId ORDER BY orderIndex ASC")
  fun getTopicsForChapter(chapterId: String): Flow<List<SyllabusTopicEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTopics(topics: List<SyllabusTopicEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTopic(topic: SyllabusTopicEntity)

  @Query("UPDATE syllabus_topics SET isCompleted = :completed WHERE id = :topicId")
  suspend fun updateTopicCompletion(topicId: String, completed: Boolean)

  @Query("UPDATE syllabus_topics SET isCompleted = :completed WHERE chapterId = :chapterId")
  suspend fun updateAllTopicsInChapter(chapterId: String, completed: Boolean)

  @Query("UPDATE syllabus_topics SET isCompleted = :completed WHERE chapterId IN (SELECT id FROM chapters WHERE subject = :subject)")
  suspend fun updateAllTopicsInSubject(subject: String, completed: Boolean)

  @Query("DELETE FROM syllabus_topics WHERE id = :topicId")
  suspend fun deleteTopic(topicId: String)

  @Query("SELECT COUNT(*) FROM syllabus_topics")
  suspend fun getTopicCount(): Int
}

@Dao
interface FormulaDao {
  @Query("SELECT * FROM formulas ORDER BY subject ASC, topic ASC, title ASC")
  fun getAllFormulas(): Flow<List<FormulaEntity>>

  @Query("SELECT * FROM formulas WHERE isBookmarked = 1 OR isSavedOffline = 1 ORDER BY subject ASC, topic ASC")
  fun getSavedFormulas(): Flow<List<FormulaEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFormulas(formulas: List<FormulaEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFormula(formula: FormulaEntity)

  @Query("UPDATE formulas SET isBookmarked = :isBookmarked WHERE id = :formulaId")
  suspend fun updateBookmarkStatus(formulaId: String, isBookmarked: Boolean)

  @Query("UPDATE formulas SET isSavedOffline = :isSavedOffline WHERE id = :formulaId")
  suspend fun updateOfflineStatus(formulaId: String, isSavedOffline: Boolean)

  @Query("SELECT COUNT(*) FROM formulas")
  suspend fun getFormulaCount(): Int
}

@Dao
interface FormulaSheetDao {
  @Query("SELECT * FROM formula_sheets ORDER BY subject ASC, title ASC")
  fun getAllFormulaSheets(): Flow<List<FormulaSheetEntity>>

  @Query("SELECT * FROM formula_sheets WHERE isDownloadedOffline = 1 ORDER BY subject ASC, title ASC")
  fun getOfflineFormulaSheets(): Flow<List<FormulaSheetEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFormulaSheets(sheets: List<FormulaSheetEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFormulaSheet(sheet: FormulaSheetEntity)

  @Query("UPDATE formula_sheets SET isDownloadedOffline = :isDownloadedOffline WHERE id = :sheetId")
  suspend fun updateSheetOfflineStatus(sheetId: String, isDownloadedOffline: Boolean)

  @Query("SELECT COUNT(*) FROM formula_sheets")
  suspend fun getFormulaSheetCount(): Int
}
