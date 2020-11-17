package com.rafay.notes.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.api.Api
import com.rafay.notes.api.LoginRequest
import com.rafay.notes.api.toLocalProfile
import com.rafay.notes.domain.AuthStore
import com.rafay.notes.domain.AuthTokenStore
import com.rafay.notes.util.CoroutineDispatchers
import java.lang.Exception
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * ViewModel for [LoginFragment]
 */
class LoginViewModel(
    private val api: Api,
    private val tokenStore: AuthTokenStore,
    private val authStore: AuthStore,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _signInState = MutableStateFlow(false)
    val signInState: StateFlow<Boolean> = _signInState.asStateFlow()

    private val _message = MutableSharedFlow<Message>()
    val message: SharedFlow<Message> = _message.asSharedFlow()

    private val _navigation = MutableSharedFlow<Navigation>()
    val navigation: SharedFlow<Navigation> = _navigation.asSharedFlow()

    /**
     * Performs login action
     */
    fun login(email: String, password: String) {
        if (validateEmail(email)) return

        viewModelScope.launch(dispatchers.io()) {
            _signInState.emit(true)

            val result = runCatching {
                requireNotNull(api.login(LoginRequest(email, password)).data).also {
                    authStore.profile = it.profile.toLocalProfile()
                    tokenStore.token = it.token

                    _navigation.emit(Navigation.Login)
                }
            }

            when (val ex = result.exceptionOrNull()) {
                is HttpException -> {
                    when (ex.code()) {
                        400 -> _message.emit(Message.BadRequest)
                    }
                }
                is Exception -> _message.emit(Message.GenericError)
            }

            _signInState.emit(false)
        }
    }

    /**
     * Checks if email is valid
     */
    private fun validateEmail(email: String): Boolean {
        return Regex("").matches(email)
    }

    enum class Message {
        BadRequest,
        GenericError
    }

    enum class Navigation {
        Login
    }
}
