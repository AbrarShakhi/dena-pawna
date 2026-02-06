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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getPersonUseCase: GetPersonUseCase, private val addPersonUseCase: AddPersonUseCase
) : ViewModel() {

    // STATE
    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state = _state.asStateFlow()

    // INTENT
    private val _intent = MutableSharedFlow<HomeIntent>()

    // EFFECT
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeIntents()
        onIntent(HomeIntent.LoadPersons)
    }

    fun onIntent(intent: HomeIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun observeIntents() {
        viewModelScope.launch {
            _intent.collect { intent ->
                when (intent) {
                    is HomeIntent.LoadPersons -> loadPersons()
                    is HomeIntent.AddPerson -> addPerson(intent.fullName, intent.phone)
                }
            }
        }
    }

    private fun loadPersons() {
        viewModelScope.launch {
            getPersonUseCase.getPersons().onStart {
                _state.update { it.copy(isLoading = true) }
            }.catch {
                _effect.emit(HomeEffect.ShowSnackBar("Failed to load persons"))
                _state.update { it.copy(isLoading = false) }
            }.collect { persons ->
                val total = persons.sumOf { it.totalAmount }
                _state.update {
                    it.copy(persons = persons, totalBalance = total, isLoading = false)
                }
            }
        }
    }

    private fun addPerson(fullName: String, phone: String) {
        viewModelScope.launch {
            addPersonUseCase.addPerson(fullName, phone).onOk {
                _effect.emit(HomeEffect.ShowSnackBar("Person added successfully"))
            }.onErr { e ->
                val message = when (e) {
                    is IllegalArgumentException -> e.message ?: "Failed to add person"
                    else -> "Failed to add person"
                }
                _effect.emit(HomeEffect.ShowSnackBar(message))
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




