package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow

class FakePersonRepository : PersonRepository {

    var addedFullName: String? = null
    var addedPhone: String? = null
    override fun getPersons(): Flow<List<Person>> {
        TODO("Not yet implemented")
    }

    override fun getPerson(): Flow<Person> {
        TODO("Not yet implemented")
    }

    override suspend fun addPerson(
        fullName: String, phone: String?
    ): Outcome<Unit, Throwable> {
        addedFullName = fullName
        addedPhone = phone
        return Outcome.ok(Unit)
    }
}
