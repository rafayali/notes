package com.rafay.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rafay.notes.domain.AuthStore

class ActivityViewModel(authStore: AuthStore) : ViewModel() {

    private val _loggedIn = MutableLiveData(authStore.loggedIn)
    val navigation: LiveData<Boolean> = _loggedIn

    enum class Navigation {
        Login,
        Home
    }
}