package com.abrarshakhi.denapawna.features.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(personId: Long) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = "PersonMapper.kt Details")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "personId ID: $personId")
    }
}
