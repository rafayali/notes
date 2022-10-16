package com.rafay.notes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity
import org.koin.android.ext.koin.androidContext

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        const val NAME = "notes"

        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}
