package com.abrarshakhi.denapawna.presentation.settings

import android.net.Uri
import com.abrarshakhi.denapawna.domain.model.AppThemeMode
import com.abrarshakhi.denapawna.mvi.UiEffect
import com.abrarshakhi.denapawna.mvi.UiIntent
import com.abrarshakhi.denapawna.mvi.UiState

class SettingsContract {
    sealed class Intent : UiIntent {
        data class UpdateTheme(val theme: AppThemeMode) : Intent()
        data class SetBiometricEnabled(val enabled: Boolean) : Intent()
        data class SetAutoLockTimeout(val timeout: Long) : Intent()
        data class SetPrivacyModeEnabled(val enabled: Boolean) : Intent()
        data class SetHapticsEnabled(val enabled: Boolean) : Intent()
        data class SetMonthlyBudget(val amount: Double) : Intent()
        object ClearAllData : Intent()
        data class ExportData(val uri: Uri, val password: CharArray) : Intent() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ExportData

                if (uri != other.uri) return false
                if (!password.contentEquals(other.password)) return false

                return true
            }

            override fun hashCode(): Int {
                var result = uri.hashCode()
                result = 31 * result + password.contentHashCode()
                return result
            }
        }

        data class ImportData(val uri: Uri, val password: CharArray) : Intent() {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as ImportData

                if (uri != other.uri) return false
                if (!password.contentEquals(other.password)) return false

                return true
            }

            override fun hashCode(): Int {
                var result = uri.hashCode()
                result = 31 * result + password.contentHashCode()
                return result
            }
        }

        data class ExportCsv(val uri: Uri) : Intent()
    }

    data class State(
        val theme: AppThemeMode = AppThemeMode.SYSTEM,
        val isBiometricEnabled: Boolean = false,
        val autoLockTimeout: Long = 0,
        val isPrivacyModeEnabled: Boolean = false,
        val isHapticsEnabled: Boolean = true,
        val monthlyBudget: Double = 0.0,
        val isExporting: Boolean = false,
        val isImporting: Boolean = false,
        val isExportingCsv: Boolean = false,
        val message: String? = null
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowToast(val message: String) : Effect()
    }
}