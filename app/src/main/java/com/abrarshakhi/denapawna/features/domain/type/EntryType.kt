package com.abrarshakhi.denapawna.features.domain.type

import com.abrarshakhi.denapawna.core.utils.isPositive

enum class EntryType {
    GIVE, TAKE
}

fun EntryType.isGiven(): Boolean = this == EntryType.GIVE

fun EntryType.explain(): String = when (this) {
    EntryType.GIVE -> "They will give me"
    EntryType.TAKE -> "I have to give them"
}

fun Double.toEntryType(): EntryType = if (this.isPositive()) EntryType.GIVE else EntryType.TAKE
