package com.rafay.notes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.common.Result
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import com.rafay.notes.util.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * [ViewModel] for [HomeFragment].
 */
class HomeViewModel(
    notesDao: NotesDao,
    dispatcher: CoroutineDispatchers
) : ViewModel() {

    private val _notes = MutableStateFlow<Result<List<NoteUiModel>>>(Result.Loading)
    val notes: StateFlow<Result<List<NoteUiModel>>> = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            notesDao.getNotesAsFlow()
                .map { Result.Success(it.map { noteEntity -> noteEntity.toNoteUiModel() }) }
                .flowOn(dispatcher.io())
                .collect { _notes.value = it }
        }
    }
}
