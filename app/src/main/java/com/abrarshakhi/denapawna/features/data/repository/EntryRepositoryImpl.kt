package com.abrarshakhi.denapawna.features.data.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.mapper.toEntity
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.repository.EntryRepository

class EntryRepositoryImpl(
    private val personDao: PersonDao, private val entryDao: EntryDao
) : EntryRepository {
    override suspend fun addEntryForAPerson(
        personId: Long, entry: Entry
    ): Outcome<Unit, Throwable> {
        try {
            entryDao.insertEntry(entry.toEntity(personId))
            return Outcome.ok(Unit)
        } catch (e: Throwable) {
            return Outcome.Err(e)
        }
    }

}