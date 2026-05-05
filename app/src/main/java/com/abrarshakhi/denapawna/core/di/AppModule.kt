package com.abrarshakhi.denapawna.core.di

import android.content.Context
import androidx.room.Room
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.local.database.Database
import com.abrarshakhi.denapawna.features.data.repository.EntryRepositoryImpl
import com.abrarshakhi.denapawna.features.data.repository.PersonRepositoryImpl
import com.abrarshakhi.denapawna.features.domain.repository.EntryRepository
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository

object AppModule {

    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext, Database::class.java, "denapawna_db"
        ).build()
    }

    fun providePersonDao(database: Database): PersonDao = database.personDao()

    fun provideEntryDao(database: Database): EntryDao = database.entryDao()

    fun providePersonRepository(personDao: PersonDao, entryDao: EntryDao): PersonRepository =
        PersonRepositoryImpl(personDao, entryDao)

    fun provideEntryRepository(personDao: PersonDao, entryDao: EntryDao): EntryRepository =
        EntryRepositoryImpl(personDao, entryDao)
}
