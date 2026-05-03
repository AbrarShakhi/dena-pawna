package com.abrarshakhi.denapawna.features.presentation.auth

sealed interface AuthIntent {
    data class Login(val email: String, val password: String) : AuthIntent
    data class Register(val email: String, val password: String, val displayName: String) : AuthIntent
    data class UpdateEmail(val value: String) : AuthIntent
    data class UpdatePassword(val value: String) : AuthIntent
    data class UpdateDisplayName(val value: String) : AuthIntent
    data object ClearError : AuthIntent
}
