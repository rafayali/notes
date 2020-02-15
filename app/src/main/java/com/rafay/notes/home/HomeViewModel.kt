package com.rafay.notes.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.rafay.notes.common.Result
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import com.rafay.notes.repository.NotesRemoteRepository
import kotlinx.coroutines.flow.map

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
