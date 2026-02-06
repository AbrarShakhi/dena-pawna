package com.abrarshakhi.denapawna.features.presentation.details

import com.abrarshakhi.denapawna.features.domain.model.Person

data class DetailsState(
    val isLoading: Boolean = true,
    val person: Person? = null,
    val error: String? = null
)
