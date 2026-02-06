package com.abrarshakhi.denapawna.features.presentation.home

sealed interface HomeEffect {
    data class ShowSnackBar(val message: String) : HomeEffect
}
