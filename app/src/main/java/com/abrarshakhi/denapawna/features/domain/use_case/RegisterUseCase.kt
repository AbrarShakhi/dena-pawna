package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import com.abrarshakhi.denapawna.features.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend fun register(
        email: String,
        password: String,
        displayName: String,
    ): Outcome<AuthUser, AuthError> {
        if (email.isBlank() || password.isBlank() || displayName.isBlank()) {
            return Outcome.err(AuthError.InvalidCredentials)
        }
        if (password.length < 6) return Outcome.err(AuthError.InvalidCredentials)
        return repository.register(email.trim(), password, displayName.trim())
    }
}
