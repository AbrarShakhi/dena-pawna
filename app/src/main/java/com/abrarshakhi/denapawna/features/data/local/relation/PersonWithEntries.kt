package com.abrarshakhi.denapawna.features.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity

data class PersonWithEntries(
    @Embedded
    val person: PersonEntity,

    @Relation(
        parentColumn = "personId",
        entityColumn = "personOwnerId"
    )
    val entries: List<EntryEntity>
)
