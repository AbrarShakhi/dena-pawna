package com.abrarshakhi.denapawna.features.domain.repository

import com.abrarshakhi.denapawna.core.utils.Outcome

interface EntryRepository {
    fun addEntryForAPerson(personId: Long): Outcome<Unit, Throwable>
}