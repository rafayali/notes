package com.rafay.notes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafay.notes.HomeActivity
import androidx.lifecycle.viewModelScope
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.toNoteUiModel
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity].
 *
 * @author Rafay Ali
 */
class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    private val _notes = MutableLiveData<List<NoteUiModel>>()
    val notes: LiveData<List<NoteUiModel>> = _notes

    init {
        viewModelScope.launch {
            notesRepository.getAll {
                _notes.postValue(it.map { todo -> todo.toNoteUiModel() })
            }
        }
    }
}