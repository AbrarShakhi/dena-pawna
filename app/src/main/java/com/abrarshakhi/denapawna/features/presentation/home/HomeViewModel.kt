package com.abrarshakhi.denapawna.features.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.core.utils.onErr
import com.abrarshakhi.denapawna.core.utils.onOk
import com.abrarshakhi.denapawna.features.domain.use_case.AddPersonUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.GetPersonUseCase
import com.abrarshakhi.denapawna.features.presentation.home.state_event.HomeUiEvent
import com.abrarshakhi.denapawna.features.presentation.home.state_event.HomeUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPersonUseCase: GetPersonUseCase,
    private val addPersonUseCase: AddPersonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadPersons()
    }

    private fun loadPersons() {
        viewModelScope.launch {
            getPersonUseCase.getPersons().onStart { _uiState.update { it.copy(isLoading = true) } }
                .catch {  }
                .collect { persons ->
                    val total = persons.sumOf { it.totalAmount }
                    _uiState.update {
                        it.copy(
                            persons = persons, totalBalance = total, isLoading = false
                        )
                    }
                }
        }
    }

    fun addPerson(fullName: String, phone: String) {
        viewModelScope.launch {
            addPersonUseCase.addPerson(fullName, phone).onOk {
                _uiEvent.emit(HomeUiEvent.ShowSnackBar("Person added successfully"))
            }.onErr { e ->
                when (e) {
                    is IllegalArgumentException -> _uiEvent.emit(
                        HomeUiEvent.ShowSnackBar(
                            e.message ?: "Failed to add person"
                        )
                    )

                    else -> _uiEvent.emit(HomeUiEvent.ShowSnackBar("Failed to add person"))
                }
            }
        }
    }


    class Factory(private val applicationContext: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                val app = applicationContext.applicationContext as DenaPawna
                return HomeViewModel(
                    getPersonUseCase = app.getPersonUseCase, addPersonUseCase = app.addPersonUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
