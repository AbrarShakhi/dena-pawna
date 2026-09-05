package com.abrarshakhi.denapawna.presentation.insights

import com.abrarshakhi.denapawna.data.local.entity.TransactionEntity
import com.abrarshakhi.denapawna.mvi.UiEffect
import com.abrarshakhi.denapawna.mvi.UiIntent
import com.abrarshakhi.denapawna.mvi.UiState
import com.abrarshakhi.denapawna.domain.SubscriptionDetector

class InsightsContract {

    sealed class Intent : UiIntent{
        object LoadInsights : Intent()
        data class SelectDay(val timestamp: Long?) : Intent()
        data class DeleteTransaction(val transaction: TransactionEntity) : Intent()
        data class UpdateTransaction(val transaction: TransactionEntity) : Intent()
        data class RestoreTransaction(val transaction: TransactionEntity) : Intent()
    }

    data class State(
        val isLoading: Boolean = true,
        val spendingVelocity: com.abrarshakhi.denapawna.presentation.dashboard.DashboardContract.VelocityData = _root_ide_package_.com.abrarshakhi.denapawna.presentation.dashboard.DashboardContract.VelocityData(),
        val netWorthHistory: List<com.abrarshakhi.denapawna.presentation.dashboard.DashboardContract.Point> = emptyList(),
        val calendarHeatmap: Map<Long, Double> = emptyMap(),
        val categoryBreakdown: List<com.abrarshakhi.denapawna.presentation.dashboard.DashboardContract.CategoryData> = emptyList(),
        val detectedSubscriptions: List<SubscriptionDetector.Subscription> = emptyList(),
        val allTransactions: List<TransactionEntity> = emptyList(),
        val selectedDayTimestamp: Long? = null
    ) : UiState {
        val selectedDayTransactions: List<TransactionEntity>
            get() = selectedDayTimestamp?.let { timestamp->
                allTransactions.filter { tx->
                    val txCal = java.util.Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                    val targetCal = java.util.Calendar.getInstance().apply { timeInMillis = tx.timestamp }
                    txCal.get(java.util.Calendar.YEAR) == targetCal.get(java.util.Calendar.YEAR) &&
                            txCal.get(java.util.Calendar.DAY_OF_YEAR) == targetCal.get(java.util.Calendar.DAY_OF_YEAR)
                }
            } ?: emptyList()
    }

    sealed class Effect : UiEffect {
        data class ShowUndoDelete(val transaction: TransactionEntity) : Effect()
    }
}