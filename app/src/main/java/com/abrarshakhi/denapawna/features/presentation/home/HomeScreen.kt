package com.abrarshakhi.denapawna.features.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrarshakhi.denapawna.features.presentation.home.composable.AddPersonBottomSheet
import com.abrarshakhi.denapawna.features.presentation.home.composable.BalanceCard
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonItem
import com.abrarshakhi.denapawna.features.presentation.home.state_event.HomeUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPersonClick: (Long) -> Unit, viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddPersonSheet by remember { mutableStateOf(false) }
    val addPersonSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is HomeUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Dena Pawna") })
    }, floatingActionButton = {
        if (!showAddPersonSheet) {
            FloatingActionButton(onClick = { showAddPersonSheet = true }) {
                Text(text = "Add Person", modifier = Modifier.padding(10.dp))
            }
        }
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        ) {

            BalanceCard(uiState.totalBalance)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Persons", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(uiState.persons) { person ->
                    PersonItem(person = person, onClick = { onPersonClick(person.id) })
                }
            }
        }
    }

    if (showAddPersonSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAddPersonSheet = false }, sheetState = addPersonSheetState
        ) {
            AddPersonBottomSheet(
                onDismiss = { showAddPersonSheet = false },
                onSave = { fullName, phone ->
                    showAddPersonSheet = false
                    viewModel.addPerson(fullName, phone)
                })
        }
    }
}

