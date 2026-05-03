package com.abrarshakhi.denapawna.features.presentation.account

sealed interface AccountEffect {
    data class ShowSnackBar(val message: String) : AccountEffect
    data object NavigateBack : AccountEffect
}
