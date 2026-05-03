package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import com.abrarshakhi.denapawna.features.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend fun login(email: String, password: String): Outcome<AuthUser, AuthError> {
        if (email.isBlank() || password.isBlank()) return Outcome.err(AuthError.InvalidCredentials)
        return repository.login(email.trim(), password)
    }
}
