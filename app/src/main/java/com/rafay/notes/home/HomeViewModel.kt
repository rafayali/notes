package com.rafay.notes.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rafay.notes.common.Result
import com.rafay.notes.db.NotesDatabase
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import com.rafay.notes.util.CoroutineDispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    notesDao: NotesDao,
    dispatcher: CoroutineDispatchers
) : ViewModel() {

    private val _notes = MutableStateFlow<Result<List<NoteUiModel>>>(Result.Loading)
    val notes: StateFlow<Result<List<NoteUiModel>>> = _notes.asStateFlow()

    private val _notesState = MutableStateFlow<List<NoteUiModel>>(listOf())
    val notesState: StateFlow<List<NoteUiModel>> = _notesState.asStateFlow()

    init {
        viewModelScope.launch {
            notesDao.getNotesAsFlow()
                .map { it.map { noteEntity -> noteEntity.toNoteUiModel() } }
                .flowOn(dispatcher.io())
                .collect { _notesState.value = it }
        }
    }
}

class HomeViewModelFactory(
    private val context: Context,
    private val dispatchers: CoroutineDispatchers
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            notesDao = NotesDatabase.getInstance(context).getNotesDao(),
            dispatcher = dispatchers
        ) as T
    }
}