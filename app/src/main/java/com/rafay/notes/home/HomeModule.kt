package com.rafay.notes.home

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(notesRepository = get()) }
}