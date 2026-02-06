package com.abrarshakhi.denapawna.features.presentation.details

sealed interface DetailsEffect {
    data class ShowSnackBar(val message: String) : DetailsEffect
}
