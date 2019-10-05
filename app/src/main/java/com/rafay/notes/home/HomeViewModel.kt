package com.rafay.notes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.common.Result
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.toNoteUiModel
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity].
 *
 * @author Rafay Ali
 */
class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    private val _notes = MutableLiveData<Result<List<NoteUiModel>>>(Result.Loading)
    val notes: LiveData<Result<List<NoteUiModel>>> = _notes

    init {
        viewModelScope.launch {
            notesRepository.getAll { notes ->
                _notes.postValue(
                    Result.Success(// simulate specific number of notes
                        notes.map {
                            it.toNoteUiModel()
                        }
                    )
                )
            }
        }
    }
}