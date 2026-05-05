package com.abrarshakhi.denapawna.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrarshakhi.denapawna.core.navigation.AppNavigation
import com.abrarshakhi.denapawna.core.ui.theme.DenaPawnaTheme
import com.abrarshakhi.denapawna.features.presentation.settings.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val theme by (application as DenaPawna).themePreferences.theme.collectAsStateWithLifecycle()
            val isDark = when (theme) {
                AppTheme.DARK -> true
                AppTheme.LIGHT -> false
                AppTheme.AUTO -> isSystemInDarkTheme()
            }
            DenaPawnaTheme(darkTheme = isDark) { AppNavigation() }
        }
    }
}