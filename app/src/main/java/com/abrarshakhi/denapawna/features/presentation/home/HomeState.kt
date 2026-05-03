package com.abrarshakhi.denapawna.features.presentation.home

import com.abrarshakhi.denapawna.features.domain.model.Person

enum class PersonFilter { ALL, RECEIVE, PAY }

data class HomeState(
    val isLoading: Boolean = false,
    val persons: List<Person> = emptyList(),
    val totalBalance: Double = 0.0,
    val totalReceive: Double = 0.0,
    val totalPay: Double = 0.0,
    val searchQuery: String = "",
    val filter: PersonFilter = PersonFilter.ALL,
)
