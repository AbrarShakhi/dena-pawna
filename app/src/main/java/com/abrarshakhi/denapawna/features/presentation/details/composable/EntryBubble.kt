package com.abrarshakhi.denapawna.features.presentation.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.core.utils.formatDateTime
import com.abrarshakhi.denapawna.features.domain.model.Entry
import com.abrarshakhi.denapawna.features.domain.type.EntryType

@Composable
fun EntryBubble(entry: Entry) {
    val isGiven = entry.type == EntryType.GIVE

    val bubbleColor = if (isGiven) RedColor else BlueColor
    val alignment = if (isGiven) Alignment.End else Alignment.Start
    val bubbleShape = if (isGiven) RoundedCornerShape(18.dp, 4.dp, 18.dp, 18.dp)
    else RoundedCornerShape(4.dp, 18.dp, 18.dp, 18.dp)

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = if (isGiven) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier.background(bubbleColor, bubbleShape)
                .padding(horizontal = 14.dp, vertical = 10.dp).widthIn(max = 260.dp)
        ) {

            // Amount (main message)
            Text(
                text = "৳ ${entry.amount}", fontSize = 18.sp, color = Color.White
            )

            Spacer(modifier = Modifier.height(2.dp))

            /*
            // Given / Taken
            Text(
                text = if (isGiven) "Given" else "Taken",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(4.dp))

             */
            // Timestamp
            Text(
                text = formatDateTime(entry.createdAt),
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.align(alignment)
            )
        }
    }
}
