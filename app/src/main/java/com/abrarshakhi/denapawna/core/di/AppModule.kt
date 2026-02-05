package com.abrarshakhi.denapawna.core.di

import android.content.Context
import androidx.room.Room
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.local.database.Database
import com.abrarshakhi.denapawna.features.data.repository.PersonRepositoryImpl
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository

object AppModule {

    // Provide Room Database
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext, Database::class.java, "denapawna_db"
        ).build()
    }

    // Provide DAO
    fun providePersonDao(database: Database): PersonDao = database.personDao()


    // Entry DAO
    fun provideEntryDao(database: Database): EntryDao = database.entryDao()


    // Provide Repository
    fun providePersonRepository(personDao: PersonDao, entryDao: EntryDao): PersonRepository =
        PersonRepositoryImpl(personDao, entryDao)

}