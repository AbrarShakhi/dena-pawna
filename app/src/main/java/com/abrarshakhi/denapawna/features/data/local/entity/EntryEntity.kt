package com.abrarshakhi.denapawna.features.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.abrarshakhi.denapawna.features.domain.type.EntryType

@Entity(
    tableName = "entries", foreignKeys = [ForeignKey(
        entity = PersonEntity::class,
        parentColumns = ["personId"],
        childColumns = ["personOwnerId"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("personOwnerId")], primaryKeys = ["personOwnerId", "createdAt"]
)
data class EntryEntity(
    val createdAt: Long = System.currentTimeMillis(),
    val personOwnerId: Long,

    val amount: Double,
    val type: EntryType
)
