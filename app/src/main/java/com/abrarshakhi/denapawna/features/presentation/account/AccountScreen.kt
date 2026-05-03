package com.abrarshakhi.denapawna.features.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.core.ui.theme.BlueColor
import com.abrarshakhi.denapawna.core.ui.theme.RedColor
import com.abrarshakhi.denapawna.features.domain.model.AuthUser
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    state: AccountState,
    effect: Flow<AccountEffect>,
    onIntent: (AccountIntent) -> Unit,
    onBack: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        effect.collect { eff ->
            when (eff) {
                is AccountEffect.ShowSnackBar -> snackbarHostState.showSnackbar(eff.message)
                is AccountEffect.NavigateBack -> onBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        val user = state.currentUser
        if (user != null) {
            ProfileContent(
                state = state,
                user = user,
                onIntent = onIntent,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    state: AccountState,
    user: AuthUser,
    onIntent: (AccountIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val initials = user.displayName
        .split(" ")
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .take(2)
        .joinToString("")

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
    ) {
        // Profile header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .background(BlueColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(initials, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = BlueColor)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(user.displayName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = listOfNotNull(user.email, user.phone).joinToString(" · "),
                fontSize = 13.sp,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(onClick = {}) {
                Text("Edit profile", fontSize = 13.sp)
            }
        }

        HorizontalDivider()

        ProfileSectionHeader("Preferences")

        ProfileRowWithControl(
            icon = "🎨",
            label = "Theme",
            control = {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.width(180.dp)) {
                    AppTheme.entries.forEachIndexed { index, theme ->
                        SegmentedButton(
                            selected = state.theme == theme,
                            onClick = { onIntent(AccountIntent.UpdateTheme(theme)) },
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

        ProfileRowWithControl(
            icon = "💱",
            label = "Currency",
            sub = "${state.currency.symbol} ${state.currency.label}",
            control = {},
        )

        ProfileRowChevron(icon = "🌐", label = "Language", sub = "English")

        HorizontalDivider()

        ProfileSectionHeader("Data")
        ProfileRowChevron(icon = "☁️", label = "Backup & Sync", sub = "Coming soon")
        ProfileRowChevron(icon = "📤", label = "Export data", sub = "CSV / PDF")

        HorizontalDivider()

        ProfileSectionHeader("About")
        ProfileRowChevron(icon = "❓", label = "Help & Support")
        ProfileRowChevron(icon = "ℹ️", label = "About", sub = "v1.0.0")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onIntent(AccountIntent.Logout) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RedColor),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("↩ Logout", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ProfileSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
private fun ProfileRowChevron(icon: String, label: String, sub: String? = null) {
    ProfileRowWithControl(
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
private fun ProfileRowWithControl(
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
