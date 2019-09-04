package com.rafay.notes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.toNoteUiModel
import com.rafay.notes.util.Result
import kotlinx.coroutines.launch
import java.util.*

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
                    Result.Success(
                        (1..10).map { // simulate specific number of notes
                            notes.first()
                                .copy(id = UUID.randomUUID().leastSignificantBits.toString())
                                .toNoteUiModel()
                        }
                    )
                )
            }
        }
    }
}