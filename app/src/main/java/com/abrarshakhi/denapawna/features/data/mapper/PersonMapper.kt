package com.abrarshakhi.denapawna.features.data.mapper

import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.model.Person


fun PersonEntity.toDomain(entries: List<EntryEntity>): Person {
    val entriesDomain = entries.map { it.toDomain() }
    return Person(
        id = this.personId,
        fullName = this.name,
        totalAmount = entriesDomain.sumOf { it.calcAmount() },
        phoneNumber = this.phone,
        entries = entriesDomain
    )
}

fun EntryEntity.toDomain(): Entry {
    return Entry(
        createdAt = this.createdAt, amount = this.amount, type = this.type
    )
}