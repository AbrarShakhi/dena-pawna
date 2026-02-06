package com.abrarshakhi.denapawna.features.presentation.details

import com.abrarshakhi.denapawna.features.domain.model.Entry

sealed interface DetailsIntent {
    data object LoadPerson : DetailsIntent
    data class AddEntry(val entry: Entry) : DetailsIntent
}
