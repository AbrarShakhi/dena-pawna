package com.abrarshakhi.denapawna.features.presentation.account

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.core.utils.onOk
import com.abrarshakhi.denapawna.features.domain.use_case.GetCurrentUserUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountEffect>()
    val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.getCurrentUser().collect { user ->
                _state.update { it.copy(currentUser = user) }
            }
        }
    }

    fun onIntent(intent: AccountIntent) {
        viewModelScope.launch {
            when (intent) {
                is AccountIntent.UpdateTheme -> _state.update { it.copy(theme = intent.theme) }
                is AccountIntent.UpdateCurrency -> _state.update { it.copy(currency = intent.currency) }
                is AccountIntent.Logout -> {
                    logoutUseCase.logout().onOk {
                        _effect.emit(AccountEffect.ShowSnackBar("Logged out successfully"))
                    }
                }
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
                val app = context.applicationContext as DenaPawna
                return AccountViewModel(
                    getCurrentUserUseCase = app.getCurrentUserUseCase,
                    logoutUseCase = app.logoutUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
