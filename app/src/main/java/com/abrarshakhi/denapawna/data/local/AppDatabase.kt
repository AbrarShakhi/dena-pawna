package com.abrarshakhi.denapawna.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abrarshakhi.denapawna.data.local.dao.MerchantAliasDao
import com.abrarshakhi.denapawna.data.local.dao.TransactionDao
import com.abrarshakhi.denapawna.data.local.entity.MerchantAliasEntity
import com.abrarshakhi.denapawna.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, MerchantAliasEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun merchantAliasDao(): MerchantAliasDao

    companion object {
        const val DATABASE_NAME = "tralance_db"
    }
}