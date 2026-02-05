package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class GetPersonUseCase(
    private val repository: PersonRepository
) {
    fun getPersons(): Flow<List<Person>> = repository.getPersons()
    fun getPerson(personId: Long): Flow<Person> = repository.getPerson(personId)
}