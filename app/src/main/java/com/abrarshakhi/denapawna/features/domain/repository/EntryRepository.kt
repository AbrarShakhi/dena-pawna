package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Entry

interface EntryRepository {
    suspend fun addEntryForAPerson(personId: Long, entry: Entry): Outcome<Unit, Throwable>
}