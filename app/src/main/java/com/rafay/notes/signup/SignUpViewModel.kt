package com.rafay.notes.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafay.notes.api.Api
import com.rafay.notes.api.RegisterRequest
import com.rafay.notes.util.CoroutineDispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val api: Api,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _state = MutableStateFlow(false)
    val signUpState: StateFlow<Boolean> = _state.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    /**
     * Performs registration processes
     */
    fun singUp(firstName: String, lastName: String?, dob: String, email: String, password: String) {
        viewModelScope.launch(dispatchers.main()) {
            val result = withContext(dispatchers.io()) {
                val response =
                    api.register(RegisterRequest(firstName, lastName, dob, email, password))

                if (response.data != null) {

                }
            }
        }
    }
}