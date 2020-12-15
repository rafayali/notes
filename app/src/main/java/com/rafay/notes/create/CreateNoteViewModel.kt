package com.rafay.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

/**
 * ViewModel for [CreateNoteFragment].
 */
class CreateNoteViewModel(
    title: String?,
    notes: String?,
    color: String?,
    private val id: Long?,
    private val notesDao: NotesDao
) : ViewModel() {

    private val _title = MutableStateFlow(title)
    val title: StateFlow<String?> = _title.asStateFlow()

    private val _notes = MutableStateFlow(notes)
    val notes: StateFlow<String?> = _notes.asStateFlow()

    private val _color = MutableStateFlow(color)
    val color: StateFlow<String?> = _color.asStateFlow()

    fun setTitle(value: String) {
        _title.value = value
    }

    fun setNotes(value: String) {
        _notes.value = value
    }

    /**
     * Sync note changes into repository.
     */
    fun sync() {
        runBlocking {
            if (noteIsValid(title.value, notes.value)) {
                saveNote()
            } else {
                deleteNote(id)
            }
        }
    }

    /**
     * Deletes [NoteEntity] from repository for given [id].
     */
    private suspend fun deleteNote(id: Long?) {
        if (id != null) {
            notesDao.delete(id)
        }
    }

    /**
     * Saves [NoteEntity] into repository.
     */
    private suspend fun saveNote() {
        val note = NoteEntity(
            id = id,
            title = title.value,
            notes = notes.value,
            colorTag = _color.value
        )

        notesDao.insert(note)
    }

    /**
     * Returns true if one of the required fields are not blank.
     */
    private fun noteIsValid(title: String?, notes: String?): Boolean {
        return title?.isNotBlank() == true || notes?.isNotBlank() == true
    }

    fun setColor(color: String) {
        _color.value = color
    }
}
