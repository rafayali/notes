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

    @ExperimentalCoroutinesApi
    val notes: LiveData<Result<List<NoteUiModel>>> = liveData {
        emitSource(
            notesRepository.observe()
                .onStart { Result.Loading }
                .map {
                    Result.Success(it.map { note -> note.toNoteUiModel() })
                }.asLiveData()
        )
    }

}