package com.abrarshakhi.denapawna.features.presentation.details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import com.abrarshakhi.denapawna.features.domain.type.isGiven


@Composable
fun EntryBubble(entry: Entry, onClick: (Entry) -> Unit, onLongClick: (Entry) -> Unit) {
    val bubbleColor = if (entry.type.isGiven()) RedColor else BlueColor
    val alignment = if (entry.type.isGiven()) Alignment.End else Alignment.Start
    val bubbleShape = if (entry.type.isGiven()) RoundedCornerShape(18.dp, 4.dp, 18.dp, 18.dp)
    else RoundedCornerShape(4.dp, 18.dp, 18.dp, 18.dp)

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp)
        .combinedClickable(
            enabled = true,
            onLongClick = { onLongClick(entry) },
            onClick = { onClick(entry) }),
        horizontalArrangement = if (entry.type.isGiven()) Arrangement.End else Arrangement.Start) {
        Column(
            modifier = Modifier.background(bubbleColor, bubbleShape)
                .padding(horizontal = 14.dp, vertical = 10.dp).widthIn(max = 260.dp)
        ) {

            Text(text = "৳ ${entry.amount}", fontSize = 18.sp, color = Color.White)

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = formatDateTime(entry.createdAt),
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.align(alignment)
            )
        }
    }
}
