package com.rafay.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.repository.NotesRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for [AddEditNoteActivity].
 */
class AddEditNoteViewModel(
    private val notesRepository: NotesRepository
) : ViewModel() {

    val title = MutableLiveData<String>()

    val notes = MutableLiveData<String>()

    private val _color = MutableLiveData<String>()
    val color: LiveData<String> = _color

    fun save(title: String, description: String? = null, color: String? = null) {
        viewModelScope.launch {
            notesRepository.create(
                title,
                description ?: "",
                false,
                color ?: "009688"
            )
        }
    }

    fun setColor(color: String){
        _color.postValue(color)
    }
}