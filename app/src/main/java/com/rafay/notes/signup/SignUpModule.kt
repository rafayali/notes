package com.rafay.notes.signup

import com.rafay.notes.api.ApiProvider
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signUpModule = module {
    viewModel { SignUpViewModel(get<ApiProvider>().api, get()) }
}
