package com.rafay.notes.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.repository.NotesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    fun save() {
        viewModelScope.launch {
            notesRepository.create(
                "Call Maggie!",
                "A test description about task.",
                false,
                "#009688"
            )
        }
    }
}