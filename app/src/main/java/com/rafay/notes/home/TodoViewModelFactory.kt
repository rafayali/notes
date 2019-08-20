package com.rafay.notes.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rafay.notes.repository.FirebaseNotesRepository
import com.rafay.notes.repository.NotesRepository

class TodoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(NotesRepository::class.java)
            .newInstance(FirebaseNotesRepository())
    }
}