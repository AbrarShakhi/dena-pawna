package com.abrarshakhi.denapawna.features.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abrarshakhi.denapawna.features.presentation.home.composable.BalanceCard
import com.abrarshakhi.denapawna.features.presentation.home.composable.PersonItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPersonClick: (Long) -> Unit, viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Dena Pawna") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {}) {

        }
    }) { paddingValues ->
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
}

