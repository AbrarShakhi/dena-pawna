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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.features.presentation.details.composable.EntryBubble
import com.abrarshakhi.denapawna.features.presentation.details.composable.EntryComposer
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailsState,
    effect: Flow<DetailsEffect>,
    onIntent: (DetailsIntent) -> Unit,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is DetailsEffect.ShowSnackBar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onBack) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
            }, title = { Text(state.person?.fullName.orEmpty()) })
        },
        bottomBar = {
            EntryComposer(
                onSendClick = { entry -> onIntent(DetailsIntent.AddEntry(entry)) },
                totalAmount = state.person?.totalAmount
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> LinearProgressIndicator()

                state.error != null -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text(state.error) }


                state.person != null -> LazyColumn(
                    modifier = Modifier, reverseLayout = true
                ) {
                    items(state.person.entries.reversed()) { entry ->
                        EntryBubble(entry, {}, {})
                    }
                }
            }
        }
    }
    // TODO: Entry Edit/delete dialog
}

