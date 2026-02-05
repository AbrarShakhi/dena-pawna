package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<List<Person>>
    fun getPerson(): Flow<Person>
    suspend fun addPerson(fullName: String, phone: String?): Outcome<Unit, Throwable>
}