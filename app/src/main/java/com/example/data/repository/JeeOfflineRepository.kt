package com.example.data.repository

import com.example.data.Chapter
import com.example.data.ChapterStatus
import com.example.data.FormulaCard
import com.example.data.SampleData
import com.example.data.SavedFormulaSheet
import com.example.data.SubjectType
import com.example.data.SyllabusTopic
import com.example.data.WeightageLevel
import com.example.data.local.ChapterEntity
import com.example.data.local.FormulaEntity
import com.example.data.local.FormulaSheetEntity
import com.example.data.local.JeeAppDatabase
import com.example.data.local.SyllabusTopicEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class JeeOfflineRepository(
  private val database: JeeAppDatabase
) {
  private val chapterDao = database.chapterDao()
  private val topicDao = database.topicDao()
  private val formulaDao = database.formulaDao()
  private val formulaSheetDao = database.formulaSheetDao()

  /**
   * Reactive Flow combining chapters and their topics from Room tables.
   */
  val chaptersFlow: Flow<List<Chapter>> = combine(
    chapterDao.getAllChapters(),
    topicDao.getAllTopics()
  ) { chapters, topics ->
    val topicsByChapter = topics.groupBy { it.chapterId }
    chapters.map { entity ->
      val mappedTopics = (topicsByChapter[entity.id] ?: emptyList()).map { topEntity ->
        SyllabusTopic(
          id = topEntity.id,
          name = topEntity.name,
          isCompleted = topEntity.isCompleted,
          isHighYield = topEntity.isHighYield,
          tag = topEntity.tag,
          keyFormulaHint = topEntity.keyFormulaHint,
          notes = topEntity.notes
        )
      }
      Chapter(
        id = entity.id,
        subject = SubjectType.values().find { it.name == entity.subject } ?: SubjectType.PHYSICS,
        name = entity.name,
        classLevel = entity.classLevel,
        weightage = WeightageLevel.values().find { it.name == entity.weightage } ?: WeightageLevel.MEDIUM,
        pyqsSolved = entity.pyqsSolved,
        totalPyqs = entity.totalPyqs,
        status = ChapterStatus.values().find { it.name == entity.status } ?: ChapterStatus.NOT_STARTED,
        revisionCount = entity.revisionCount,
        keyFormulaCount = entity.keyFormulaCount,
        keyConcepts = entity.keyConcepts,
        topics = mappedTopics
      )
    }
  }

  /**
   * Reactive Flow of all cached formulas from Room database.
   */
  val formulasFlow: Flow<List<FormulaCard>> = formulaDao.getAllFormulas().map { entities ->
    entities.map { entity ->
      FormulaCard(
        id = entity.id,
        subject = SubjectType.values().find { it.name == entity.subject } ?: SubjectType.PHYSICS,
        topic = entity.topic,
        title = entity.title,
        formula = entity.formula,
        explanation = entity.explanation,
        unitsAndConstants = entity.unitsAndConstants,
        isBookmarked = entity.isBookmarked,
        isSavedOffline = entity.isSavedOffline
      )
    }
  }

  /**
   * Reactive Flow of all cached formula sheets from Room database.
   */
  val formulaSheetsFlow: Flow<List<SavedFormulaSheet>> = formulaSheetDao.getAllFormulaSheets().map { entities ->
    entities.map { entity ->
      SavedFormulaSheet(
        id = entity.id,
        title = entity.title,
        subject = SubjectType.values().find { it.name == entity.subject } ?: SubjectType.PHYSICS,
        description = entity.description,
        formulaCount = entity.formulaCount,
        formulaIds = entity.formulaIds,
        isDownloadedOffline = entity.isDownloadedOffline,
        category = entity.category,
        lastUpdatedFormatted = entity.lastUpdatedFormatted,
        downloadSizeKb = entity.downloadSizeKb
      )
    }
  }

  /**
   * Seeds the database with default sample chapters, topics, formulas and formula sheets
   * if the local Room cache is empty.
   */
  suspend fun initializeDatabaseIfEmpty() = withContext(Dispatchers.IO) {
    val chapterCount = chapterDao.getChapterCount()
    if (chapterCount == 0) {
      val chapterEntities = mutableListOf<ChapterEntity>()
      val topicEntities = mutableListOf<SyllabusTopicEntity>()

      SampleData.chapters.forEach { chapter ->
        chapterEntities.add(
          ChapterEntity(
            id = chapter.id,
            subject = chapter.subject.name,
            name = chapter.name,
            classLevel = chapter.classLevel,
            weightage = chapter.weightage.name,
            pyqsSolved = chapter.pyqsSolved,
            totalPyqs = chapter.totalPyqs,
            status = chapter.status.name,
            revisionCount = chapter.revisionCount,
            keyFormulaCount = chapter.keyFormulaCount,
            keyConcepts = chapter.keyConcepts
          )
        )
        chapter.topics.forEachIndexed { index, topic ->
          topicEntities.add(
            SyllabusTopicEntity(
              id = topic.id,
              chapterId = chapter.id,
              name = topic.name,
              isCompleted = topic.isCompleted,
              isHighYield = topic.isHighYield,
              tag = topic.tag,
              keyFormulaHint = topic.keyFormulaHint,
              notes = topic.notes,
              orderIndex = index
            )
          )
        }
      }
      chapterDao.insertChapters(chapterEntities)
      topicDao.insertTopics(topicEntities)
    }

    val formulaCount = formulaDao.getFormulaCount()
    if (formulaCount == 0) {
      val formulaEntities = SampleData.formulas.map { card ->
        FormulaEntity(
          id = card.id,
          subject = card.subject.name,
          topic = card.topic,
          title = card.title,
          formula = card.formula,
          explanation = card.explanation,
          unitsAndConstants = card.unitsAndConstants,
          isBookmarked = card.isBookmarked,
          isSavedOffline = true
        )
      }
      formulaDao.insertFormulas(formulaEntities)
    }

    val sheetCount = formulaSheetDao.getFormulaSheetCount()
    if (sheetCount == 0) {
      val sheetEntities = SampleData.savedFormulaSheets.map { sheet ->
        FormulaSheetEntity(
          id = sheet.id,
          title = sheet.title,
          subject = sheet.subject.name,
          description = sheet.description,
          formulaCount = sheet.formulaCount,
          formulaIds = sheet.formulaIds,
          isDownloadedOffline = sheet.isDownloadedOffline,
          category = sheet.category,
          lastUpdatedFormatted = sheet.lastUpdatedFormatted,
          downloadSizeKb = sheet.downloadSizeKb
        )
      }
      formulaSheetDao.insertFormulaSheets(sheetEntities)
    }
  }

  suspend fun toggleTopicCompletion(chapterId: String, topicId: String, completed: Boolean) = withContext(Dispatchers.IO) {
    topicDao.updateTopicCompletion(topicId, completed)
  }

  suspend fun markAllTopicsInChapter(chapterId: String, completed: Boolean) = withContext(Dispatchers.IO) {
    topicDao.updateAllTopicsInChapter(chapterId, completed)
    chapterDao.updateChapterStatus(
      chapterId = chapterId,
      status = if (completed) ChapterStatus.MASTERED.name else ChapterStatus.NOT_STARTED.name
    )
  }

  suspend fun markAllSubjectTopics(subject: SubjectType, completed: Boolean) = withContext(Dispatchers.IO) {
    topicDao.updateAllTopicsInSubject(subject.name, completed)
  }

  suspend fun addCustomTopic(chapterId: String, topic: SyllabusTopic) = withContext(Dispatchers.IO) {
    topicDao.insertTopic(
      SyllabusTopicEntity(
        id = topic.id,
        chapterId = chapterId,
        name = topic.name,
        isCompleted = topic.isCompleted,
        isHighYield = topic.isHighYield,
        tag = topic.tag,
        keyFormulaHint = topic.keyFormulaHint,
        notes = topic.notes,
        orderIndex = 999
      )
    )
  }

  suspend fun updateChapterStatus(chapterId: String, newStatus: ChapterStatus) = withContext(Dispatchers.IO) {
    chapterDao.updateChapterStatus(chapterId, newStatus.name)
  }

  suspend fun updateChapterPyqs(chapterId: String, pyqsSolved: Int, status: ChapterStatus) = withContext(Dispatchers.IO) {
    chapterDao.updateChapterPyqs(chapterId, pyqsSolved, status.name)
  }

  suspend fun toggleFormulaBookmark(formulaId: String, isBookmarked: Boolean) = withContext(Dispatchers.IO) {
    formulaDao.updateBookmarkStatus(formulaId, isBookmarked)
  }

  suspend fun toggleFormulaOffline(formulaId: String, isSavedOffline: Boolean) = withContext(Dispatchers.IO) {
    formulaDao.updateOfflineStatus(formulaId, isSavedOffline)
  }

  suspend fun toggleSheetOffline(sheetId: String, isDownloadedOffline: Boolean) = withContext(Dispatchers.IO) {
    formulaSheetDao.updateSheetOfflineStatus(sheetId, isDownloadedOffline)
  }

  suspend fun saveCustomFormulaSheet(sheet: SavedFormulaSheet) = withContext(Dispatchers.IO) {
    formulaSheetDao.insertFormulaSheet(
      FormulaSheetEntity(
        id = sheet.id,
        title = sheet.title,
        subject = sheet.subject.name,
        description = sheet.description,
        formulaCount = sheet.formulaCount,
        formulaIds = sheet.formulaIds,
        isDownloadedOffline = sheet.isDownloadedOffline,
        category = sheet.category,
        lastUpdatedFormatted = sheet.lastUpdatedFormatted,
        downloadSizeKb = sheet.downloadSizeKb
      )
    )
  }
}
