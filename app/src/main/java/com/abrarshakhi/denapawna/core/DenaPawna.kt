package com.abrarshakhi.denapawna.core

import android.app.Application
import com.abrarshakhi.denapawna.core.di.AppModule
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.domain.repository.EntryRepository
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import com.abrarshakhi.denapawna.features.domain.use_case.AddEntryUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.AddPersonUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.GetPersonUseCase

class DenaPawna : Application() {
    private val database by lazy { AppModule.provideDatabase(this) }
    private val personDao: PersonDao by lazy { AppModule.providePersonDao(database) }
    private val entryDao: EntryDao by lazy { AppModule.provideEntryDao(database) }
    private val personRepository: PersonRepository by lazy {
        AppModule.providePersonRepository(personDao, entryDao)
    }
    private val entryRepository: EntryRepository by lazy {
        AppModule.provideEntryRepository(personDao, entryDao)
    }
    val getPersonUseCase: GetPersonUseCase by lazy { GetPersonUseCase(repository = personRepository) }
    val addPersonUseCase: AddPersonUseCase by lazy { AddPersonUseCase(repository = personRepository) }
    val addEntryUseCase: AddEntryUseCase by lazy { AddEntryUseCase(repository = entryRepository) }
}