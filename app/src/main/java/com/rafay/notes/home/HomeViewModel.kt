package com.rafay.notes.home

import androidx.lifecycle.*
import com.rafay.notes.common.Result
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeActivity].
 */
class HomeViewModel(
    notesDao: NotesDao
) : ViewModel() {

    val notes: LiveData<Result<List<NoteUiModel>>> = liveData {
        emitSource(
            notesDao.getNotes().map {
                Result.Success(it.map { noteEntity -> noteEntity.toNoteUiModel() })
            }.asLiveData()
        )
    }
}
