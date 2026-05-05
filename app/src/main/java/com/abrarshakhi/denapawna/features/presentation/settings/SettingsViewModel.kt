package com.abrarshakhi.denapawna.features.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val themePreferences: ThemePreferences) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState(theme = themePreferences.theme.value))
    val state = _state.asStateFlow()

    fun onIntent(intent: SettingsIntent) {
        viewModelScope.launch {
            when (intent) {
                is SettingsIntent.UpdateTheme -> {
                    themePreferences.setTheme(intent.theme)
                    _state.update { it.copy(theme = intent.theme) }
                }
                is SettingsIntent.UpdateCurrency -> _state.update { it.copy(currency = intent.currency) }
            }
        }
    }

    class Factory(private val themePreferences: ThemePreferences) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                return SettingsViewModel(themePreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
