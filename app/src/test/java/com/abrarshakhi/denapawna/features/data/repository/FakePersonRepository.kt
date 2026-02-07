package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class FakePersonRepository : PersonRepository {

    var addedPerson: Person? = null
        private set

    override fun getPersons(): Flow<List<Person>> {
        TODO("Not yet implemented")
    }

    override fun getPerson(personId: Long): Flow<Person> {
        TODO("Not yet implemented")
    }

    override suspend fun addPerson(person: Person): Outcome<Unit, Throwable> {
        addedPerson = person
        return Outcome.ok(Unit)
    }

    override suspend fun deletePerson(personId: Long): Outcome<Unit, Throwable> {
        return Outcome.ok(Unit)
    }
}
