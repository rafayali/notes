package com.rafay.notes.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.api.Api
import com.rafay.notes.api.RegisterRequest
import com.rafay.notes.api.toLocalProfile
import com.rafay.notes.domain.AuthStore
import com.rafay.notes.domain.AuthTokenStore
import com.rafay.notes.ktx.ErrorMessage
import com.rafay.notes.ktx.toError
import com.rafay.notes.util.CoroutineDispatchers
import com.rafay.notes.util.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val api: Api,
    private val tokenStore: AuthTokenStore,
    private val authStore: AuthStore,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _message = MutableSharedFlow<ErrorMessage>()
    val message: SharedFlow<ErrorMessage> = _message.asSharedFlow()

    private val _navigation = MutableStateFlow<Event<Navigation>?>(null)
    val navigation: StateFlow<Event<Navigation>?> = _navigation.asStateFlow()

    /**
     * Performs registration processes.
     */
    fun singUp(firstName: String, lastName: String?, dob: String, email: String, password: String) {
        viewModelScope.launch(dispatchers.main()) {
            val result = runCatching {
                val response = api.register(
                    RegisterRequest(firstName, lastName, dob, email, password)
                )

                requireNotNull(response.data).also {
                    tokenStore.token = it.token
                    authStore.profile = it.profile.toLocalProfile()

                    _navigation.emit(Event(Navigation.Home))
                }
            }

            result.toError()?.let { _message.emit(it) }
        }
    }

    enum class Navigation {
        Home
    }
}
