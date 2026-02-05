package com.abrarshakhi.denapawna.features.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.features.presentation.details.composable.EntryBubble
import com.abrarshakhi.denapawna.features.presentation.details.composable.EntryComposer
import com.abrarshakhi.denapawna.features.presentation.details.state_event.DetailsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBack: () -> Unit, viewModel: DetailsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onBack) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
            }, title = {
                when (val state = uiState) {
                    is DetailsUiState.Success -> Text(state.person.fullName)
                    else -> {}
                }
            })
        },
        bottomBar = {
            EntryComposer(
                onSendClick = { entry -> viewModel.addEntry(entry) },
                totalAmount = when (val state = uiState) {
                    is DetailsUiState.Success -> state.person.totalAmount
                    else -> null
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (val state = uiState) {
                is DetailsUiState.Loading -> {
                    LinearProgressIndicator(modifier = Modifier.padding(paddingValues))
                }

                is DetailsUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) { Text(text = state.error) }
                }

                is DetailsUiState.Success -> {
                    val person = state.person

                    LazyColumn(modifier = Modifier, reverseLayout = true) {
                        items(person.entries.reversed()) { entry ->
                            EntryBubble(entry = entry)
                        }
                    }
                }
            }
        }
    }
}

