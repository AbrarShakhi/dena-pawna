package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend fun logout(): Outcome<Unit, AuthError> = repository.logout()
}
