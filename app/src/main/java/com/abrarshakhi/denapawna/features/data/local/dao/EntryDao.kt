package com.abrarshakhi.denapawna.features.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryEntity)

    @Query("SELECT * FROM entries WHERE personOwnerId = :personId ORDER BY createdAt DESC")
    fun getEntriesForPerson(personId: Long): Flow<List<EntryEntity>>
}
