package com.abrarshakhi.denapawna.features.presentation.home.state_event

sealed interface HomeUiEvent {
    data class ShowSnackBar(val message: String) : HomeUiEvent
}
