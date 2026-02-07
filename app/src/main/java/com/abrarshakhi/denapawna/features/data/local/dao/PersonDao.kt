package com.abrarshakhi.denapawna.features.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity
import com.abrarshakhi.denapawna.features.data.local.relation.PersonWithEntries
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: PersonEntity)

    @Query("SELECT * FROM persons ORDER BY createdAt DESC")
    fun getPersons(): Flow<List<PersonEntity>>

    @Transaction
    @Query("SELECT * FROM persons WHERE personId = :personId")
    fun getPersonWithEntries(personId: Long): Flow<PersonWithEntries>

    @Transaction
    @Query("SELECT * FROM persons ORDER BY createdAt DESC")
    fun getAllPersonsWithEntries(): Flow<List<PersonWithEntries>>

    @Query("DELETE FROM persons WHERE personId = :personId")
    suspend fun deletePerson(personId: Long)
}
