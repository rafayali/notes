package com.rafay.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        const val NAME = "notes"
    }
}
