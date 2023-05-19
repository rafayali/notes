package com.rafay.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        private const val NAME = "notes"

        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    NAME
                ).fallbackToDestructiveMigration().build()
            }
        }
    }
}
