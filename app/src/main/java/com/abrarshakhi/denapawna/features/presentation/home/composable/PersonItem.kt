package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.core.utils.isPositive
import com.abrarshakhi.denapawna.features.domain.model.Person

@Composable
fun PersonItem(
    person: Person, onClick: () -> Unit, onLongClick: () -> Unit
) {
    val isPositive = person.totalAmount.isPositive()
    val amountColor = if (isPositive) BlueColor else RedColor
    val amountPrefix = if (isPositive) "+" else "−"
    val initials = person.fullName
        .split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .combinedClickable(enabled = true, onLongClick = onLongClick, onClick = onClick),
        shape = RoundedCornerShape(14.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(amountColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = initials,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = amountColor,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = person.fullName, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                if (!person.phoneNumber.isNullOrBlank()) {
                    Text(text = person.phoneNumber, fontSize = 12.sp, color = Color.Gray)
                }
            }

            Text(
                text = "$amountPrefix৳ ${person.totalAmount}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(R.drawable.outline_arrow_forward_24),
                contentDescription = null,
                tint = Color.Gray,
            )
        }
    }
}
