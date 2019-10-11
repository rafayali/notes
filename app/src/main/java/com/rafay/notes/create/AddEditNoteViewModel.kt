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

    fun save(title: String, description: String? = null, colorHex: String? = null) {
        viewModelScope.launch {
            notesRepository.create(
                title,
                description ?: "",
                false,
                colorHex ?: "#009688"
            )
        }
    }
}