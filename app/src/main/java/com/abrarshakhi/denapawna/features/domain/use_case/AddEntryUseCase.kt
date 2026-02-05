package com.abrarshakhi.denapawna.features.domain.use_case

import com.abrarshakhi.denapawna.core.utils.Outcome
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.repository.EntryRepository

class AddEntryUseCase(
    private val repository: EntryRepository
) {
    suspend fun addEntry(personId: Long, entry: Entry): Outcome<Unit, Throwable> {
        if (entry.amount <= 0) {
            return Outcome.err(IllegalArgumentException("Amount must me grater than 0"))
        }
        return repository.addEntryForAPerson(personId, entry)
    }
}