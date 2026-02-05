package com.abrarshakhi.denapawna.features.presentation.details.composable

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun EntryTypeButton(
    text: String, selected: Boolean, onClick: () -> Unit
) {
    FilterChip(
        selected = selected, onClick = onClick, label = { Text(text) })
}
