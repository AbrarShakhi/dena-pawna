package com.abrarshakhi.denapawna.features.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onIntent: (SettingsIntent) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            SettingsSectionHeader("Preferences")

            SettingsRowWithControl(
                icon = "🎨",
                label = "Theme",
                control = {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.width(180.dp)) {
                        AppTheme.entries.forEachIndexed { index, theme ->
                            SegmentedButton(
                                selected = state.theme == theme,
                                onClick = { onIntent(SettingsIntent.UpdateTheme(theme)) },
                                shape = SegmentedButtonDefaults.itemShape(index, AppTheme.entries.size),
                                label = {
                                    Text(
                                        text = theme.name.lowercase().replaceFirstChar { it.uppercase() },
                                        fontSize = 11.sp,
                                    )
                                },
                            )
                        }
                    }
                },
            )

            SettingsRowWithControl(
                icon = "💱",
                label = "Currency",
                sub = "${state.currency.symbol} ${state.currency.label}",
                control = {},
            )

            SettingsRowChevron(icon = "🌐", label = "Language", sub = "English")

            HorizontalDivider()

            SettingsSectionHeader("Data")
            SettingsRowChevron(icon = "☁️", label = "Backup & Sync", sub = "Coming soon")
            SettingsRowChevron(icon = "📤", label = "Export data", sub = "CSV / PDF")

            HorizontalDivider()

            SettingsSectionHeader("About")
            SettingsRowChevron(icon = "❓", label = "Help & Support")
            SettingsRowChevron(icon = "ℹ️", label = "About", sub = "v1.0.0")

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
private fun SettingsRowChevron(icon: String, label: String, sub: String? = null) {
    SettingsRowWithControl(
        icon = icon,
        label = label,
        sub = sub,
        control = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray,
            )
        },
    )
}

@Composable
private fun SettingsRowWithControl(
    icon: String,
    label: String,
    sub: String? = null,
    control: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(icon, fontSize = 18.sp, modifier = Modifier.width(32.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 15.sp)
            if (sub != null) Text(sub, fontSize = 12.sp, color = Color.Gray)
        }
        control()
    }
}
