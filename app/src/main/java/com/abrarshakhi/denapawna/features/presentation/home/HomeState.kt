package com.abrarshakhi.denapawna.features.presentation.home

import com.abrarshakhi.denapawna.features.domain.model.Person

data class HomeState(
    val isLoading: Boolean = false,
    val persons: List<Person> = emptyList(),
    val totalBalance: Double = 0.0
)
