package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.features.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<List<Person>>
}