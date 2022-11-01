package com.rafay.notes.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rafay.notes.db.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Suppress("unused")
    @Update
    suspend fun update(note: NoteEntity)

    @Query("DELETE FROM notes where id = :id")
    suspend fun delete(id: Long): Int

    @Query("SELECT * FROM notes")
    fun getNotesAsFlow(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes")
    suspend fun getNotes(): List<NoteEntity>

    @Query("SELECT * FROM notes where id = :id")
    suspend fun getNote(id: Long): NoteEntity
}
