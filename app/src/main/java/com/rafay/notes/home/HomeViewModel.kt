package com.rafay.notes.home

import androidx.lifecycle.*
import com.rafay.notes.common.Result
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import com.rafay.notes.repository.NotesRemoteRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * [ViewModel] for [HomeActivity].
 */
class HomeViewModel(notesRemoteRepository: NotesRemoteRepository, notesDao: NotesDao) :
    ViewModel() {

    val notesLocal: LiveData<Result<List<NoteUiModel>>> = liveData {
        emitSource(
            notesDao.getNotes().map {
                Result.Success(it.map { noteEntity -> noteEntity.toNoteUiModel() })
            }.asLiveData()
        )
    }
}
