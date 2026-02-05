package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity
import com.abrarshakhi.denapawna.features.data.mapper.toDomain
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class PersonRepositoryImpl(
    private val personDao: PersonDao, private val entryDao: EntryDao
) : PersonRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPersons(): Flow<List<Person>> =
        personDao.getAllPersonsWithEntries().mapLatest { list ->
            list.map { (person, entries) ->
                person.toDomain(entries)
            }
        }

    override fun getPerson(): Flow<Person> {
        TODO("Not yet implemented")
    }

    override suspend fun addPerson(
        fullName: String, phone: String?
    ): Outcome<Unit, Throwable> {
        try {
            personDao.insertPerson(PersonEntity(name = fullName, phone = phone))
            return Outcome.ok(Unit)
        } catch (e: Throwable) {
            return Outcome.err(e)
        }
    }

}