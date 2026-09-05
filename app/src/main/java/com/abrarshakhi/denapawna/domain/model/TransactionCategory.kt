package com.abrarshakhi.denapawna.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Brush
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.MoneyOff
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class TransactionCategory(
    val displayName: String,
    val icon: ImageVector,
    val color: Color
) {
    WEB_DEV("Web Development", Icons.Rounded.Language, Color(0xFF42A5F5)),
    APP_DEV("App Development", Icons.Rounded.PhoneAndroid, Color(0xFF26A69A)),
    DESIGN("UI/UX Design", Icons.Rounded.Brush, Color(0xFFAB47BC)),
    EDITING("Video Editing", Icons.Rounded.Movie, Color(0xFFFF7043)),
    CONTENT("Content Writing", Icons.Rounded.Edit, Color(0xFF8D6E63)),
    MARKETING("Marketing", Icons.Rounded.Campaign, Color(0xFFE91E63)),

    INCOME("Income", Icons.Rounded.AttachMoney, Color(0xFF66BB6A)),
    EXPENSE("Expense", Icons.Rounded.MoneyOff, Color(0xFFEF5350)),

    OTHERS("Others", Icons.Rounded.Category, Color(0xFF9E9E9E));

    companion object {
        fun fromString(name: String?): TransactionCategory {
            return entries.find { it.name.equals(name, ignoreCase = true) } ?: OTHERS
        }
    }
}
