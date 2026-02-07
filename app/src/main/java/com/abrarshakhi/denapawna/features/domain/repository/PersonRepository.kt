package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getPersons(): Flow<List<Person>>
    fun getPerson(personId: Long): Flow<Person>
    suspend fun addPerson(person: Person): Outcome<Unit, Throwable>
    suspend fun deletePerson(personId: Long): Outcome<Unit, Throwable>
}