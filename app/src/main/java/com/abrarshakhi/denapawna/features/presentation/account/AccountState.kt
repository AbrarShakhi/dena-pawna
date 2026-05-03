package com.abrarshakhi.denapawna.features.presentation.account

import com.abrarshakhi.denapawna.features.domain.model.AuthUser

enum class AppTheme { LIGHT, DARK, AUTO }
enum class AppCurrency(val symbol: String, val label: String) {
    BDT("৳", "Bangladeshi Taka"),
    USD("$", "US Dollar"),
    INR("₹", "Indian Rupee"),
}

data class AccountState(
    val currentUser: AuthUser? = null,
    val theme: AppTheme = AppTheme.LIGHT,
    val currency: AppCurrency = AppCurrency.BDT,
)
