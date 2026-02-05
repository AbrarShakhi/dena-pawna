package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import toDomain

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
}