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
    WEB_DEV("Web Development", Icons.Rounded.Language, Color(0xFF38BDF8)),
    APP_DEV("App Development", Icons.Rounded.PhoneAndroid, Color(0xFF34D399)),
    DESIGN("UI/UX Design", Icons.Rounded.Brush, Color(0xFFA78BFA)),
    EDITING("Video Editing", Icons.Rounded.Movie, Color(0xFFFB923C)),
    CONTENT("Content Writing", Icons.Rounded.Edit, Color(0xFFFBBF24)),
    MARKETING("Marketing", Icons.Rounded.Campaign, Color(0xFFF472B6)),

    INCOME("Income", Icons.Rounded.AttachMoney, Color(0xFF10B981)),
    EXPENSE("Expense", Icons.Rounded.MoneyOff, Color(0xFFF43F5E)),

    OTHERS("Others", Icons.Rounded.Category, Color(0xFFA1A1AA));

    companion object {
        fun fromString(name: String?): TransactionCategory {
            return entries.find { it.name.equals(name, ignoreCase = true) } ?: OTHERS
        }
    }
}
