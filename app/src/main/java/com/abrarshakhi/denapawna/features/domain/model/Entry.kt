package com.abrarshakhi.denapawna.features.domain.model

import com.abrarshakhi.denapawna.features.domain.type.EntryType

data class Entry(
    val createdAt: Long, val amount: Double, val type: EntryType
) {
    companion object {
        fun new(amount: Double, type: EntryType): Entry {
            return Entry(
                createdAt = System.currentTimeMillis(), amount = amount, type = type
            )
        }
    }

    fun calcAmount(): Double {
        return if (type == EntryType.GIVE) {
            amount
        } else {
            amount * -1
        }
    }
}