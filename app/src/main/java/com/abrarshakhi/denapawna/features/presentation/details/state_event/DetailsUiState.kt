package com.abrarshakhi.denapawna.features.presentation.details.state_event

import com.abrarshakhi.denapawna.features.domain.model.Person

sealed interface DetailsUiState {
    object Loading : DetailsUiState
    data class Success(val person: Person) : DetailsUiState
    data class Error(val error: String) : DetailsUiState
}