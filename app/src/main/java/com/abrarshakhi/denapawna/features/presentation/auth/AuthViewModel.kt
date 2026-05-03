package com.abrarshakhi.denapawna.features.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.core.utils.onErr
import com.abrarshakhi.denapawna.core.utils.onOk
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.use_case.LoginUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onIntent(intent: AuthIntent) {
        viewModelScope.launch {
            when (intent) {
                is AuthIntent.UpdateEmail -> _state.update { it.copy(emailInput = intent.value, error = null) }
                is AuthIntent.UpdatePassword -> _state.update { it.copy(passwordInput = intent.value, error = null) }
                is AuthIntent.UpdateDisplayName -> _state.update { it.copy(displayNameInput = intent.value, error = null) }
                is AuthIntent.ClearError -> _state.update { it.copy(error = null) }
                is AuthIntent.Login -> performLogin(intent.email, intent.password)
                is AuthIntent.Register -> performRegister(intent.email, intent.password, intent.displayName)
            }
        }
    }

    private suspend fun performLogin(email: String, password: String) {
        _state.update { it.copy(isLoading = true, error = null) }
        loginUseCase.login(email, password)
            .onOk { _state.update { it.copy(isLoading = false, emailInput = "", passwordInput = "") } }
            .onErr { error -> _state.update { it.copy(isLoading = false, error = error.toMessage()) } }
    }

    private suspend fun performRegister(email: String, password: String, displayName: String) {
        _state.update { it.copy(isLoading = true, error = null) }
        registerUseCase.register(email, password, displayName)
            .onOk { _state.update { it.copy(isLoading = false, emailInput = "", passwordInput = "", displayNameInput = "") } }
            .onErr { error -> _state.update { it.copy(isLoading = false, error = error.toMessage()) } }
    }

    private fun AuthError.toMessage(): String = when (this) {
        is AuthError.InvalidCredentials -> "Invalid email or password"
        is AuthError.UserAlreadyExists -> "An account with this email already exists"
        is AuthError.UserNotFound -> "No account found with this email"
        is AuthError.Unknown -> "Something went wrong. Please try again."
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                val app = context.applicationContext as DenaPawna
                return AuthViewModel(
                    loginUseCase = app.loginUseCase,
                    registerUseCase = app.registerUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
