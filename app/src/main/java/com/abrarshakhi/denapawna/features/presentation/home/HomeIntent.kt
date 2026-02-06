package com.abrarshakhi.denapawna.features.presentation.home

sealed interface HomeIntent {
    data object LoadPersons : HomeIntent
    data class AddPerson(val fullName: String, val phone: String) : HomeIntent
}
