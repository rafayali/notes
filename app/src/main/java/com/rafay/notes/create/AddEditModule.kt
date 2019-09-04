package com.rafay.notes.create

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val addEditModule = module {
    viewModel { AddEditNoteViewModel(repository = get()) }
}