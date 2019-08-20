package com.rafay.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.Note

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(repository: NotesRepository) : ViewModel() {

    private val _todo = MutableLiveData<Note>()
    val note: LiveData<Note> = _todo
}