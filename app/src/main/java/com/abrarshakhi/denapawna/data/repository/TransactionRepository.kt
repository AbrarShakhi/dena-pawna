package com.abrarshakhi.denapawna.data.repository

import com.abrarshakhi.denapawna.data.local.dao.MerchantAliasDao
import com.abrarshakhi.denapawna.data.local.dao.TransactionDao
import com.abrarshakhi.denapawna.data.local.entity.MerchantAliasEntity
import com.abrarshakhi.denapawna.data.local.entity.TransactionEntity
import com.abrarshakhi.denapawna.domain.CategorizerEngine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val merchantAliasDao: MerchantAliasDao,
    private val categorizerEngine: CategorizerEngine
) {
    fun getAllTransactions(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()

    fun getRecentTransactions(limit: Int): Flow<List<TransactionEntity>> =
        transactionDao.getRecentTransactions(limit)

    suspend fun insertTransaction(transaction: TransactionEntity) {
        val rawMerchant = transaction.merchant.uppercase().trim()

        val alias = merchantAliasDao.getAliasForRawName(rawMerchant)
        val finalMerchant: String
        val finalCategory: String

        if (alias != null) {
            finalMerchant = alias.cleanName
            finalCategory = transaction.category.ifBlank {
                categorizerEngine.categorize(alias.cleanName).name
            }
        } else {
            val cleanName = autoCleanMerchantName(transaction.merchant)
            val autoCategory = categorizerEngine.categorize(cleanName)

            if (cleanName != transaction.merchant) {
                merchantAliasDao.insertAlias(MerchantAliasEntity(rawMerchant, cleanName))
            }
            finalMerchant = cleanName
            finalCategory =
                transaction.category.ifBlank { autoCategory.name }
        }

        transactionDao.insertTransaction(
            transaction.copy(
                merchant = finalMerchant,
                category = finalCategory
            )
        )
    }

    suspend fun updateTransaction(transaction: TransactionEntity) {
        transactionDao.insertTransaction(transaction)
    }

    private fun autoCleanMerchantName(raw: String): String {
        return raw.split("*", "-", "  ")
            .filter { it.isNotBlank() && it.length > 2 }
            .firstOrNull { it.any { char -> char.isLetter() } }
            ?.lowercase()
            ?.replaceFirstChar { it.uppercase() }
            ?: raw
    }

    suspend fun deleteTransaction(transaction: TransactionEntity) =
        transactionDao.deleteTransaction(transaction)

    fun getTotalExpenses(): Flow<Double?> = transactionDao.getTotalExpenses()

    fun getTotalIncome(): Flow<Double?> = transactionDao.getTotalIncome()

    fun getExpensesSince(startTime: Long): Flow<List<TransactionEntity>> =
        transactionDao.getExpensesSince(startTime)
}