package com.abrarshakhi.denapawna.features.presentation.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.type.EntryType
import com.abrarshakhi.denapawna.features.domain.type.explain
import com.abrarshakhi.denapawna.features.domain.type.toEntryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryComposer(
    onSendClick: (Entry) -> Unit, totalAmount: Double?
) {
    var amountText by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(EntryType.GIVE) }

    val amount = amountText.toDoubleOrNull()
    val isAmountValid = amount != null && amount > 0

    val accentColor = if (selectedType == EntryType.GIVE) RedColor else BlueColor

    Column(
        modifier = Modifier.fillMaxWidth().imePadding().background(
            color = androidx.compose.ui.graphics.Color(0xFFF7F7F7),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ).padding(12.dp).navigationBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            EntryTypePill(
                text = "Give", selected = selectedType == EntryType.GIVE, color = RedColor
            ) { selectedType = EntryType.GIVE }

            Spacer(modifier = Modifier.width(8.dp))

            EntryTypePill(
                text = "Take", selected = selectedType == EntryType.TAKE, color = BlueColor
            ) { selectedType = EntryType.TAKE }

            Spacer(modifier = Modifier.weight(1f))

            totalAmount?.let {
                Text(
                    text = it.toEntryType().explain() + ": $totalAmount",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                placeholder = { Text("Enter amount") },
                leadingIcon = { Text("৳") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true,
                shape = RoundedCornerShape(50)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    onSendClick(
                        Entry.new(
                            amount = amount ?: 0.0, type = selectedType
                        )
                    )
                    amountText = ""
                },
                enabled = isAmountValid,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = accentColor
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier.height(52.dp)
            ) {
                Text("Add")
            }
        }
    }
}
