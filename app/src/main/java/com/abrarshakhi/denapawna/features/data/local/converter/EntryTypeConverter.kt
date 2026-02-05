package com.abrarshakhi.denapawna.features.data.local.converter

import androidx.room.TypeConverter
import com.abrarshakhi.denapawna.features.domain.type.EntryType

class EntryTypeConverter {

    @TypeConverter
    fun fromEntryType(type: EntryType): String = type.name

    @TypeConverter
    fun toEntryType(value: String): EntryType = EntryType.valueOf(value)
}