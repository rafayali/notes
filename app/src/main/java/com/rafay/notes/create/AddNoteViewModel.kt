package com.rafay.notes.create

import android.content.Context
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

/**
 * ViewModel for [AddNoteScreen].
 */
class AddNoteViewModel(
    private val id: Long?,
    private val notesDao: NotesDao
) : ViewModel() {

    private val _title = MutableStateFlow(EMPTY_STRING)
    val title: StateFlow<String> = _title.asStateFlow()

    private val _notes = MutableStateFlow(EMPTY_STRING)
    val notes: StateFlow<String> = _notes.asStateFlow()

    private val _color = MutableStateFlow<String?>(null)
    val color: StateFlow<String?> = _color.asStateFlow()

    init {
        if(id != null){
            viewModelScope.launch {
                val note = notesDao.getNote(id = id)
                _title.value = note.title
                _notes.value = note.notes
            }
        }
    }

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
            colorTag = null
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

class AddNoteModelFactory(private val context: Context, private val noteId: Long? = null) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddNoteViewModel(
            id = noteId,
            notesDao = NotesDatabase.getInstance(context).getNotesDao()
        ) as T
    }
}