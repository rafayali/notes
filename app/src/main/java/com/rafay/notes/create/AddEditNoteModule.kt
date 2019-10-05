package com.rafay.notes.create

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEditNoteModule = module {
    viewModel { AddEditNoteViewModel(notesRepository = get()) }
}