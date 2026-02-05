package com.abrarshakhi.denapawna.features.domain.model

import com.abrarshakhi.denapawna.features.domain.type.EntryType

data class Person(
    val id: Long,
    val fullName: String,
    val phoneNumber: String?,
    val totalAmount: Double = 0.0,
    val entries: List<Entry>
)
