package com.abrarshakhi.denapawna.features.domain.model

sealed class AuthError {
    data object InvalidCredentials : AuthError()
    data class UserAlreadyExists(val email: String) : AuthError()
    data object UserNotFound : AuthError()
    data class Unknown(val cause: Throwable) : AuthError()
}
