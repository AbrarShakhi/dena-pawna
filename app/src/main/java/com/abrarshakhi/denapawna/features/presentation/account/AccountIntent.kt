package com.abrarshakhi.denapawna.features.presentation.account

sealed interface AccountIntent {
    data object Logout : AccountIntent
    data class UpdateTheme(val theme: AppTheme) : AccountIntent
    data class UpdateCurrency(val currency: AppCurrency) : AccountIntent
}
