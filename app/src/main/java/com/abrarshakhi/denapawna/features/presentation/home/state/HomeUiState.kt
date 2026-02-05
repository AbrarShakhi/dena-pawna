package com.abrarshakhi.denapawna.features.presentation.home.state

import com.abrarshakhi.denapawna.features.domain.model.Person

data class HomeUiState(
    val persons: List<Person> = emptyList(),
    val totalBalance: Double = 0.0,
    val isLoading: Boolean = false
)