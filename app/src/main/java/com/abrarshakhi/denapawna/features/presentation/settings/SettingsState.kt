package com.abrarshakhi.denapawna.features.presentation.settings

enum class AppTheme { LIGHT, DARK, AUTO }

enum class AppCurrency(val symbol: String, val label: String) {
    BDT("৳", "Bangladeshi Taka"),
    USD("$", "US Dollar"),
    INR("₹", "Indian Rupee"),
}

data class SettingsState(
    val theme: AppTheme = AppTheme.AUTO,
    val currency: AppCurrency = AppCurrency.BDT,
)
