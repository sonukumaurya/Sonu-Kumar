package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
  entities = [
    ChapterEntity::class,
    SyllabusTopicEntity::class,
    FormulaEntity::class,
    FormulaSheetEntity::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class JeeAppDatabase : RoomDatabase() {

  abstract fun chapterDao(): ChapterDao
  abstract fun topicDao(): TopicDao
  abstract fun formulaDao(): FormulaDao
  abstract fun formulaSheetDao(): FormulaSheetDao

  companion object {
    @Volatile
    private var INSTANCE: JeeAppDatabase? = null

    fun getInstance(context: Context): JeeAppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          JeeAppDatabase::class.java,
          "jee_prep_offline.db"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
