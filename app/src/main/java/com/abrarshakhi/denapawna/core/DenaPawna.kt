package com.abrarshakhi.denapawna.core

import android.app.Application
import com.abrarshakhi.denapawna.core.di.AppModule
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository

class DenaPawna : Application() {
    val database by lazy { AppModule.provideDatabase(this) }
    val personDao: PersonDao by lazy { AppModule.providePersonDao(database) }
    val entryDao: EntryDao by lazy { AppModule.provideEntryDao(database) }
    val personRepository: PersonRepository by lazy {
        AppModule.providePersonRepository(personDao, entryDao)
    }
}