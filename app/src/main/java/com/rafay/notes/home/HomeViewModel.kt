package com.rafay.notes.home

import androidx.lifecycle.*
import com.rafay.notes.common.Result
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.repository.models.toNoteUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity].
 */
class HomeViewModel(notesRepository: NotesRepository) : ViewModel() {

    private val _notes = MutableLiveData<Result<List<NoteUiModel>>>(Result.Loading)
    val notes: LiveData<Result<List<NoteUiModel>>> = _notes

    @ExperimentalCoroutinesApi
    val notesLiveDataBuilder: LiveData<Result<List<NoteUiModel>>> = liveData {
        emitSource(
            notesRepository.observe()
                .onStart { Result.Loading }
                .map {
                    Result.Success(it.map { note -> note.toNoteUiModel() })
                }.asLiveData()
        )
    }

    init {
        viewModelScope.launch {
            notesRepository.observe { notes ->
                _notes.postValue(
                    Result.Success(
                        notes.map {
                            it.toNoteUiModel()
                        }
                    )
                )
            }
        }
    }
}