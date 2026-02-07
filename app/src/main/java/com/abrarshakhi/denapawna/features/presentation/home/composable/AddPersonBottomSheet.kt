package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.features.domain.model.Person

@Composable
fun AddPersonBottomSheet(
    person: Person, onDismiss: () -> Unit, onSave: (Person) -> Unit
) {
    var personState by remember { mutableStateOf(person) }
    var phoneNumberState by remember { mutableStateOf(person.phoneNumber ?: "") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)
    ) {

        Text(
            text = "Add New Person", fontSize = 22.sp, fontWeight = FontWeight.Bold
        )

        Text(
            text = "Keep track of who owes how much",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Full Name
        OutlinedTextField(
            value = personState.fullName,
            onValueChange = { personState = person.copy(fullName = it) },
            label = { Text("Full Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone Number
        OutlinedTextField(
            value = phoneNumberState,
            onValueChange = { phoneNumberState = it },
            label = { Text("Phone Number (optional)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Actions
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onDismiss, modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }

            Button(
                onClick = { onSave(personState) },
                enabled = personState.fullName.isNotBlank(),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
