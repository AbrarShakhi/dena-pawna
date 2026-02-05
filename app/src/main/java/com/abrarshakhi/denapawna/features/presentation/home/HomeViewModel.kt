package com.abrarshakhi.denapawna.features.presentation.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.features.domain.repository.PersonRepository
import com.abrarshakhi.denapawna.features.presentation.home.state.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PersonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        loadPeople()
    }

    private fun loadPeople() {
        viewModelScope.launch {
            repository.getPersons().onStart { _uiState.update { it.copy(isLoading = true) } }
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


    class Factory(private val context: Context) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                val app = context.applicationContext as DenaPawna
                val repository = app.personRepository
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
