package com.abrarshakhi.denapawna.features.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val emailInput: String = "",
    val passwordInput: String = "",
    val displayNameInput: String = "",
    val error: String? = null,
)
