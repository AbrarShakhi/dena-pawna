package com.abrarshakhi.denapawna.features.domain.model

import com.abrarshakhi.denapawna.features.domain.type.EntryType

data class Entry(
    val createdAt: Long,
    val amount: Double,
    val type: EntryType,
    val note: String? = null,
)