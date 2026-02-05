package com.abrarshakhi.denapawna.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDateTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
