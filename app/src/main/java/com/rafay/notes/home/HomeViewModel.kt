package com.rafay.notes.home

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rafay.notes.db.NotesDatabase
import com.rafay.notes.db.dao.NotesDao
import com.rafay.notes.db.entities.toNoteUiModel
import com.rafay.notes.util.CoroutineDispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    notesDao: NotesDao, dispatcher: CoroutineDispatchers
) : ViewModel() {
    private val _state = mutableStateOf<List<NoteUiModel>>(listOf())
    val state: State<List<NoteUiModel>> = _state

    init {
        viewModelScope.launch {
            notesDao.getNotesAsFlow().map { it.map { noteEntity -> noteEntity.toNoteUiModel() } }
                .distinctUntilChanged()
                .flowOn(dispatcher.io()).collect { _state.value = it }
        }
    }
}

class HomeViewModelFactory(
    private val context: Context, private val dispatchers: CoroutineDispatchers
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            notesDao = NotesDatabase.getInstance(context).getNotesDao(), dispatcher = dispatchers
        ) as T
    }
}