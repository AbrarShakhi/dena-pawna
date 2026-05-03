package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.AuthError
import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    fun getCurrentUser(): StateFlow<AuthUser?>
    suspend fun login(email: String, password: String): Outcome<AuthUser, AuthError>
    suspend fun register(email: String, password: String, displayName: String): Outcome<AuthUser, AuthError>
    suspend fun logout(): Outcome<Unit, AuthError>
}