package com.abrarshakhi.denapawna.features.presentation.home

import com.abrarshakhi.denapawna.features.domain.model.Person

sealed interface HomeIntent {
    data object LoadPersons : HomeIntent
    data class DeletePerson(val personId: Long) : HomeIntent
    data class AddPerson(val person: Person) : HomeIntent
}
