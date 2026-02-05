package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.R
import com.abrarshakhi.denapawna.features.domain.model.Person

@Composable
fun PersonItem(
    person: Person, onClick: () -> Unit
) {
    val amountColor = if (person.totalAmount >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable { onClick() },
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = person.fullName, fontSize = 16.sp, fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (person.totalAmount >= 0) "Will give you money"
                    else "You owe money", fontSize = 12.sp, color = Color.Gray
                )
            }

            Text(
                text = "৳ ${person.totalAmount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.outline_arrow_forward_24),
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
