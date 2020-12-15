package com.rafay.notes.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.api.Api
import com.rafay.notes.api.LoginRequest
import com.rafay.notes.api.toLocalProfile
import com.rafay.notes.domain.AuthStore
import com.rafay.notes.domain.AuthTokenStore
import com.rafay.notes.ktx.ErrorMessage
import com.rafay.notes.ktx.toError
import com.rafay.notes.util.CoroutineDispatchers
import com.rafay.notes.util.isEmail
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for [LoginFragment]
 */
class LoginViewModel(
    private val api: Api,
    private val tokenStore: AuthTokenStore,
    private val authStore: AuthStore,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableSharedFlow<ErrorMessage>()
    val error: SharedFlow<ErrorMessage> = _error.asSharedFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation: SharedFlow<Navigation> = _navigation.asSharedFlow()

    /**
     * Performs login action
     */
    fun login(email: String, password: String) {
        if (!email.isEmail()) return

        viewModelScope.launch(dispatchers.io()) {
            _loading.emit(true)

            val result = runCatching {
                requireNotNull(api.login(LoginRequest(email, password)).data).also {
                    authStore.profile = it.profile.toLocalProfile()
                    tokenStore.token = it.token

                    _navigation.emit(Navigation.Login)
                }
            }

            result.toError()?.let { _error.emit(it) }

            _loading.emit(false)
        }
    }

    enum class Navigation {
        Login
    }
}
