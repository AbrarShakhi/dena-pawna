package com.abrarshakhi.denapawna.features.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.core.utils.isPositive

@Composable
fun BalanceCard(total: Double, totalReceive: Double, totalPay: Double) {
    val color = if (total.isPositive()) BlueColor else RedColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Balance", color = Color.Gray)
            Text(
                text = "৳ $total",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = color,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                BalancePill(
                    label = "Will receive",
                    amount = totalReceive,
                    color = BlueColor,
                    modifier = Modifier.weight(1f),
                )
                BalancePill(
                    label = "Will pay",
                    amount = totalPay,
                    color = RedColor,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BalancePill(label: String, amount: Double, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.15f)),
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Text(text = label, fontSize = 11.sp, color = Color.Gray)
            Text(text = "৳ $amount", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = color)
        }
    }
}
