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
import com.abrarshakhi.denapawna.features.presentation.details.state_event.DetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val personId: Long,
    private val getPersonUseCase: GetPersonUseCase,
    private val addEntryUseCase: AddEntryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadPerson()
    }

    private fun loadPerson() {
        viewModelScope.launch {
            getPersonUseCase.getPerson(personId).catch { e ->
                _uiState.update { DetailsUiState.Error(e.message ?: "Something went wrong") }
            }.collect { person ->
                _uiState.update { DetailsUiState.Success(person) }
            }
        }
    }

    fun addEntry(entry: Entry) {
        viewModelScope.launch {
            addEntryUseCase.addEntry(personId = personId, entry = entry).onOk {
                loadPerson()
            }.onErr {

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
