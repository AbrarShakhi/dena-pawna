package com.abrarshakhi.denapawna.features.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.core.ui.theme.RedColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    state: AuthState,
    onIntent: (AuthIntent) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
                navigationIcon = {
                    IconButton(onClick = {
                        onIntent(AuthIntent.ClearError)
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Text("Join Dena Pawna", fontSize = 22.sp)
            Text("Track who owes what", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.displayNameInput,
                onValueChange = { onIntent(AuthIntent.UpdateDisplayName(it)) },
                label = { Text("Full name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.emailInput,
                onValueChange = { onIntent(AuthIntent.UpdateEmail(it)) },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.passwordInput,
                onValueChange = { onIntent(AuthIntent.UpdatePassword(it)) },
                label = { Text("Password (min. 6 characters)") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            if (state.error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = state.error, color = RedColor, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onIntent(
                        AuthIntent.Register(
                            email = state.emailInput,
                            password = state.passwordInput,
                            displayName = state.displayNameInput,
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
            ) {
                Text("Create Account")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = {
                onIntent(AuthIntent.ClearError)
                onBack()
            }) {
                Text("Already have an account? Sign in")
            }
        }
    }
}
