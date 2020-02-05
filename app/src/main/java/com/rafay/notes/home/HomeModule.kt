package com.rafay.notes.home

import com.rafay.notes.db.NotesDatabase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            notesRemoteRepository = get(),
            notesDao = get<NotesDatabase>().getNotesDao()
        )
    }
}
