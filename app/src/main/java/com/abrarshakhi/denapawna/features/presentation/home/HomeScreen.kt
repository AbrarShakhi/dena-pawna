package com.abrarshakhi.denapawna.features.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.features.domain.model.Person
import com.abrarshakhi.denapawna.features.presentation.home.composable.AddPersonBottomSheet
import com.abrarshakhi.denapawna.features.presentation.home.composable.BalanceCard
import com.abrarshakhi.denapawna.features.presentation.home.composable.PeopleChart
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonDialog
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonItem
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    effect: Flow<HomeEffect>,
    onIntent: (HomeIntent) -> Unit,
    onPersonClick: (Long) -> Unit,
    onAccountClick: () -> Unit,
) {
    var showAddPersonSheet: Person? by remember { mutableStateOf(null) }
    val addPersonSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val snackbarHostState = remember { SnackbarHostState() }
    var showPersonDialog: Person? by remember { mutableStateOf(null) }

    val displayedPersons = remember(state.persons, state.searchQuery, state.filter) {
        var result = state.persons
        if (state.searchQuery.isNotBlank()) {
            result = result.filter { it.fullName.contains(state.searchQuery, ignoreCase = true) }
        }
        result = when (state.filter) {
            PersonFilter.ALL -> result
            PersonFilter.RECEIVE -> result.filter { it.totalAmount > 0 }
            PersonFilter.PAY -> result.filter { it.totalAmount < 0 }
        }
        result
    }

    LaunchedEffect(Unit) {
        effect.collect { effect ->
            when (effect) {
                is HomeEffect.ShowSnackBar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onIntent(HomeIntent.UpdateSearchQuery(it)) },
                        placeholder = { Text("Search persons", color = Color.Gray) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                actions = {
                    IconButton(onClick = onAccountClick) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Account",
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            if (showAddPersonSheet == null) {
                FloatingActionButton(onClick = {
                    showAddPersonSheet = Person(id = 0, fullName = "", phoneNumber = "")
                }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_article_person_24),
                        contentDescription = "Add Person",
                    )
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 16.dp)) {

            BalanceCard(
                total = state.totalBalance,
                totalReceive = state.totalReceive,
                totalPay = state.totalPay,
            )

            Spacer(modifier = Modifier.height(12.dp))

            FilterChipsRow(
                current = state.filter,
                totalCount = state.persons.size,
                receiveCount = state.persons.count { it.totalAmount > 0 },
                payCount = state.persons.count { it.totalAmount < 0 },
                onSelect = { onIntent(HomeIntent.UpdateFilter(it)) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                item {
                    PeopleChart(persons = state.persons)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(displayedPersons) { person ->
                    PersonItem(
                        person = person,
                        onClick = { onPersonClick(person.id) },
                        onLongClick = { showPersonDialog = person },
                    )
                }
            }
        }
    }

    if (showAddPersonSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { showAddPersonSheet = null },
            sheetState = addPersonSheetState,
        ) {
            AddPersonBottomSheet(
                person = showAddPersonSheet!!,
                onDismiss = { showAddPersonSheet = null },
                onSave = {
                    showAddPersonSheet = null
                    onIntent(HomeIntent.AddPerson(it))
                },
            )
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
            },
        )
    }
}

@Composable
private fun FilterChipsRow(
    current: PersonFilter,
    totalCount: Int,
    receiveCount: Int,
    payCount: Int,
    onSelect: (PersonFilter) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilterChip(
            selected = current == PersonFilter.ALL,
            onClick = { onSelect(PersonFilter.ALL) },
            label = { Text("All ($totalCount)") },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BlueColor.copy(alpha = 0.15f),
                selectedLabelColor = BlueColor,
            ),
        )
        FilterChip(
            selected = current == PersonFilter.RECEIVE,
            onClick = { onSelect(PersonFilter.RECEIVE) },
            label = { Text("↑ Receive ($receiveCount)") },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = BlueColor.copy(alpha = 0.15f),
                selectedLabelColor = BlueColor,
            ),
        )
        FilterChip(
            selected = current == PersonFilter.PAY,
            onClick = { onSelect(PersonFilter.PAY) },
            label = { Text("↓ Pay ($payCount)") },
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = RedColor.copy(alpha = 0.15f),
                selectedLabelColor = RedColor,
            ),
        )
    }
}
