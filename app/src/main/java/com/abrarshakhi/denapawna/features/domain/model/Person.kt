package com.abrarshakhi.denapawna.features.domain.model

import com.abrarshakhi.denapawna.features.domain.type.EntryType

data class Person(
    val id: Long, val fullName: String, val totalAmount: Double = 0.0, val entries: List<Entry>
)

data class Entry(
    val createdAt: Long,
    val amount: Double,
    val type: EntryType,
    val note: String? = null,
)
