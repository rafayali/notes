package com.rafay.notes.login

import com.rafay.notes.api.ApiProvider
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module(override = true) {
    viewModel {
        LoginViewModel(
            api = get<ApiProvider>().api,
            dispatchers = get(),
            tokenStore = get(),
            authStore = get()
        )
    }
}