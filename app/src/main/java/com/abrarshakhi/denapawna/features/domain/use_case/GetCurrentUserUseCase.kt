package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import com.abrarshakhi.denapawna.features.domain.repository.AuthRepository
import kotlinx.coroutines.flow.StateFlow

class GetCurrentUserUseCase(private val repository: AuthRepository) {
    fun getCurrentUser(): StateFlow<AuthUser?> = repository.getCurrentUser()
}
