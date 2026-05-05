package com.abrarshakhi.denapawna.features.presentation.settings

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemePreferences(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _theme = MutableStateFlow(
        prefs.getString(KEY_THEME, AppTheme.AUTO.name)
            ?.let { runCatching { AppTheme.valueOf(it) }.getOrDefault(AppTheme.AUTO) }
            ?: AppTheme.AUTO
    )
    val theme = _theme.asStateFlow()

    fun setTheme(theme: AppTheme) {
        prefs.edit().putString(KEY_THEME, theme.name).apply()
        _theme.value = theme
    }

    companion object {
        private const val KEY_THEME = "theme"
    }
}
