package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity
import com.abrarshakhi.denapawna.features.data.mapper.toDomain
import com.abrarshakhi.denapawna.features.data.mapper.toEntity
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPerson(personId: Long): Flow<Person> {
        return personDao.getPersonWithEntries(personId = personId).mapLatest { (person, entries) ->
            person.toDomain(entries)
        }
    }

    override suspend fun addPerson(person: Person): Outcome<Unit, Throwable> {
        try {
            personDao.insertPerson(person.toEntity())
            return Outcome.ok(Unit)
        } catch (e: Throwable) {
            return Outcome.err(e)
        }
    }

    override suspend fun deletePerson(personId: Long): Outcome<Unit, Throwable> {
        try {
            personDao.deletePerson(personId)
            return Outcome.ok(Unit)
        } catch (e: Throwable) {
            return Outcome.err(e)
        }
    }
}