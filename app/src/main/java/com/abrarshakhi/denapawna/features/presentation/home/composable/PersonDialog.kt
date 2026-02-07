package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.abrarshakhi.denapawna.features.domain.model.Person

@Composable
fun PersonDialog(
    person: Person, onDismiss: () -> Unit, onDelete: (Person) -> Unit, onEdit: (Person) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = person.fullName, style = MaterialTheme.typography.titleLarge
                )

                person.phoneNumber?.let {
                    Text(
                        text = "📞 $it", style = MaterialTheme.typography.bodyMedium
                    )
                }

                Text(
                    text = "Amount: ৳${person.totalAmount}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Entries: ${person.entries.size}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                    TextButton(onClick = { onEdit(person) }) {
                        Text("Edit")
                    }
                    TextButton(onClick = { onDelete(person) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
