package com.rafay.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.repository.NotesRepository
import com.rafay.notes.util.Event
import kotlinx.coroutines.launch

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(
    title: String?,
    notes: String?,
    color: String,
    private val id: String?,
    private val notesRepository: NotesRepository
) : ViewModel() {

    val title = MutableLiveData<String>(title)

    val notes = MutableLiveData<String>(notes)

    private val _color = MutableLiveData<String>(color)
    val color: LiveData<String> = _color

    fun save() {
        viewModelScope.launch {
            notesRepository.createOrUpdate(
                id,
                title.value ?: "",
                notes.value!!,
                false,
                _color.value!!
            )
        }
    }

    fun setColor(color: String) {
        _color.postValue(color)
    }

    companion object {
        const val DEFAULT_COLOR = "009688"
    }
}