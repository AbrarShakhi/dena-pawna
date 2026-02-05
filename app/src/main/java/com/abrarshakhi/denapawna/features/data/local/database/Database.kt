package com.abrarshakhi.denapawna.features.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abrarshakhi.denapawna.features.data.local.converter.EntryTypeConverter
import com.abrarshakhi.denapawna.features.data.local.dao.EntryDao
import com.abrarshakhi.denapawna.features.data.local.dao.PersonDao
import com.abrarshakhi.denapawna.features.data.local.entity.EntryEntity
import com.abrarshakhi.denapawna.features.data.local.entity.PersonEntity

@Database(
    entities = [PersonEntity::class, EntryEntity::class], version = 1, exportSchema = false
)
@TypeConverters(EntryTypeConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun entryDao(): EntryDao
}
