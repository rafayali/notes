package com.rafay.notes.create

import androidx.lifecycle.*
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.Note
import kotlinx.coroutines.*

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(repository: NotesRepository) : ViewModel() {

    private val _todo = MutableLiveData<Note>()
    val note: LiveData<Note> = _todo
}