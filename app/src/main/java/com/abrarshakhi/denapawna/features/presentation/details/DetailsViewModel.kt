package com.abrarshakhi.denapawna.features.presentation.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.core.utils.onErr
import com.abrarshakhi.denapawna.core.utils.onOk
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.use_case.AddEntryUseCase
import com.abrarshakhi.denapawna.features.domain.use_case.GetPersonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val personId: Long,
    private val getPersonUseCase: GetPersonUseCase,
    private val addEntryUseCase: AddEntryUseCase,
) : ViewModel() {

    // STATE
    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    // INTENT
    private val _intent = MutableSharedFlow<DetailsIntent>()

    // EFFECT
    private val _effect = MutableSharedFlow<DetailsEffect>()
    val effect = _effect.asSharedFlow()

    init {
        observeIntents()
        onIntent(DetailsIntent.LoadPerson)
    }

    fun onIntent(intent: DetailsIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun observeIntents() {
        viewModelScope.launch {
            _intent.collect { intent ->
                when (intent) {
                    is DetailsIntent.LoadPerson -> loadPerson()
                    is DetailsIntent.AddEntry -> addEntry(intent.entry)
                }
            }
        }
    }

    private fun loadPerson() {
        viewModelScope.launch {
            getPersonUseCase.getPerson(personId).catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false, error = e.message ?: "Something went wrong"
                        )
                    }
                }.collect { person ->
                    _state.update {
                        it.copy(
                            isLoading = false, person = person, error = null
                        )
                    }
                }
        }
    }

    private fun addEntry(entry: Entry) {
        viewModelScope.launch {
            addEntryUseCase.addEntry(personId = personId, entry = entry).onOk {
                    onIntent(DetailsIntent.LoadPerson)
                }.onErr {
                    _effect.emit(
                        DetailsEffect.ShowSnackBar("Failed to add entry")
                    )
                }
        }
    }

    class Factory(private val applicationContext: Context, private val personId: Long) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                val app = applicationContext.applicationContext as DenaPawna
                return DetailsViewModel(
                    personId = personId,
                    getPersonUseCase = app.getPersonUseCase,
                    addEntryUseCase = app.addEntryUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
