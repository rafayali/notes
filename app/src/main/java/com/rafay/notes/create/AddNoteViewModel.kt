package com.rafay.notes.create

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rafay.notes.db.NotesDatabase
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity
import com.rafay.notes.util.EMPTY_STRING
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

data class Note(
    val id: Int? = null,
    val noteId: Long? = null,
    val title: String = EMPTY_STRING,
    val notes: String = EMPTY_STRING,
    val colorTag: String? = null
) {
    /**
     * Returns true if one of the required fields are not blank.
     */
    fun isValid() = title.isNotBlank() || notes.isNotBlank()
}

/**
 * ViewModel for [AddNoteScreen].
 */
class AddNoteViewModel(
    private val id: Int?,
    private val notesDao: NotesDao
) : ViewModel() {
    private val _note = mutableStateOf(Note(id = id))
    val note: State<Note> = _note

    init {
        if (id != null) {
            viewModelScope.launch {
                notesDao.getNote(id = id)?.let { noteEntity ->
                    _note.value = Note(
                        id = noteEntity.id,
                        noteId = noteEntity.noteId,
                        title = noteEntity.title,
                        notes = noteEntity.notes,
                        colorTag = noteEntity.colorTag,
                    )
                }
            }
        }
    }

    fun setTitle(value: String) {
        _note.value = _note.value.copy(title = value)
    }

    fun setNotes(value: String) {
        _note.value = _note.value.copy(notes = value)
    }

    /**
     * Sync note changes into repository.
     */
    fun sync() {
        runBlocking {
            if (_note.value.isValid()) {
                saveNote()
            } else {
                deleteNote(_note.value.id)
            }
        }
    }

    /**
     * Deletes [NoteEntity] from repository for given [id].
     */
    private suspend fun deleteNote(id: Int?) {
        if (id != null) {
            notesDao.delete(id)
        }
    }

    /**
     * Saves [NoteEntity] into repository.
     */
    private suspend fun saveNote() {
        val note = NoteEntity(
            noteId = UUID.randomUUID().leastSignificantBits,
            title = _note.value.title,
            notes = _note.value.notes,
            colorTag = null
        )

        notesDao.insert(note)
    }

    fun setColor(color: String) {
        _note.value = _note.value.copy(colorTag = color)
    }
}

class AddNoteModelFactory(private val context: Context, private val id: Int? = null) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddNoteViewModel(
            id = id,
            notesDao = NotesDatabase.getInstance(context).getNotesDao()
        ) as T
    }
}