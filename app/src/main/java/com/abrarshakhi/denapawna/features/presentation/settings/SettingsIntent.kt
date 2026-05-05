package com.abrarshakhi.denapawna.features.presentation.settings

sealed interface SettingsIntent {
    data class UpdateTheme(val theme: AppTheme) : SettingsIntent
    data class UpdateCurrency(val currency: AppCurrency) : SettingsIntent
}
