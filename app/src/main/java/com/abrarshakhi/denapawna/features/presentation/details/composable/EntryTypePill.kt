package com.abrarshakhi.denapawna.features.presentation.details.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun EntryTypePill(
    text: String,
    selected: Boolean,
    color: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (selected) color else androidx.compose.ui.graphics.Color.LightGray
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text)
    }
}