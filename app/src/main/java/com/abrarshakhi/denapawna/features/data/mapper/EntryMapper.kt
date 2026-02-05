package com.abrarshakhi.denapawna.features.data.mapper

import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.model.Person


fun Entry.toEntity(personId: Long): EntryEntity {
    return EntryEntity(
        createdAt = this.createdAt,
        personOwnerId = personId,
        amount = this.amount,
        type = this.type
    )
}
