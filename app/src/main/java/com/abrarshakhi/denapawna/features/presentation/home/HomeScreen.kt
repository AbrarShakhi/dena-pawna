package com.abrarshakhi.denapawna.features.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.presentation.home.composable.AddPersonBottomSheet
import com.abrarshakhi.denapawna.features.presentation.home.composable.BalanceCard
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonDialog
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonItem
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    effect: Flow<HomeEffect>,
    onIntent: (HomeIntent) -> Unit,
    onPersonClick: (Long) -> Unit
) {
    var showAddPersonSheet: Person? by remember { mutableStateOf(null) }
    val addPersonSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val snackbarHostState = remember { SnackbarHostState() }

    var showPersonDialog: Person? by remember { mutableStateOf(null) }

    // EFFECTS (one-time events)
    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Dena Pawna") })
    }, floatingActionButton = {
        if (showAddPersonSheet == null) {
            FloatingActionButton(onClick = {
                showAddPersonSheet = Person(
                    id = 0, fullName = "", phoneNumber = ""
                )
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_article_person_24),
                    contentDescription = "Add Person"
                )
            }
        }
    }, snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp)
        ) {

            BalanceCard(state.totalBalance)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Persons", fontSize = 18.sp, fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(state.persons) { person ->
                    PersonItem(
                        person = person,
                        onClick = { onPersonClick(person.id) },
                        onLongClick = { showPersonDialog = person })
                }
            }
        }
    }

    if (showAddPersonSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { showAddPersonSheet = null }, sheetState = addPersonSheetState
        ) {
            AddPersonBottomSheet(
                person = showAddPersonSheet!!,
                onDismiss = { showAddPersonSheet = null },
                onSave = { it ->
                    showAddPersonSheet = null
                    onIntent(
                        HomeIntent.AddPerson(it)
                    )
                })
        }
    }
    if (showPersonDialog != null) {
        PersonDialog(
            person = showPersonDialog!!,
            onDismiss = { showPersonDialog = null },
            onDelete = {
                onIntent(HomeIntent.DeletePerson(personId = it.id))
                showPersonDialog = null
            },
            onEdit = {
                showAddPersonSheet = it
                showPersonDialog = null
            })
    }

}
