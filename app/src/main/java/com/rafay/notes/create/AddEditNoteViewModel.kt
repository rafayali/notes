package com.rafay.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.NoteEntity
import kotlinx.coroutines.runBlocking

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(
    title: String?,
    notes: String?,
    color: String,
    private val id: Long?,
    private val notesDao: NotesDao
) : ViewModel() {

    private val _title = MutableLiveData<String>(title)
    val title: LiveData<String> = Transformations.distinctUntilChanged(_title)

    private val _notes = MutableLiveData<String>(notes)
    val notes: LiveData<String> = Transformations.distinctUntilChanged(_notes)

    private val _color = MutableLiveData<String>(color)
    val color: LiveData<String> = _color

    fun setTitle(value: String){
        _title.postValue(value)
    }

    fun setNotes(value: String){
        _notes.postValue(value)
    }

    fun save() {
        runBlocking {
            if (noteIsValid(title.value, notes.value)) {
                saveNote()
            } else {
                deleteNote(id)
            }
        }
    }

    private suspend fun deleteNote(id: Long?) {
        if (id != null) {
            notesDao.delete(id)
        }
    }

    private suspend fun saveNote() {
        val note = NoteEntity(
            id = id,
            title = title.value,
            notes = notes.value
        )

        notesDao.insert(note)
    }

    /**
     * Returns true if one of the fields are not blank.
     */
    private fun noteIsValid(title: String?, notes: String?): Boolean {
        return title?.isNotBlank() == true || notes?.isNotBlank() == true
    }

    fun setColor(color: String) {
        _color.postValue(color)
    }

    companion object {
        const val DEFAULT_COLOR = "009688"
    }
}
