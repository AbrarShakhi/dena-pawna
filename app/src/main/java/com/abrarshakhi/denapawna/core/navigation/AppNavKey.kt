package com.abrarshakhi.denapawna.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavKey : NavKey {

    @Serializable
    data object Home : AppNavKey()

    @Serializable
    data class Detail(val personId: Long) : AppNavKey()
}
