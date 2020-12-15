package com.rafay.notes

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel { ActivityViewModel(get()) }
}