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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abrarshakhi.denapawna.core.ui.theme.RedColor

@Composable
fun LoginScreen(
    state: AuthState,
    onIntent: (AuthIntent) -> Unit,
    onNavigateToSignup: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Dena Pawna", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Sign in to continue", fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)

        Spacer(modifier = Modifier.height(36.dp))

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
            label = { Text("Password") },
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
            onClick = { onIntent(AuthIntent.Login(state.emailInput, state.passwordInput)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = {
            onIntent(AuthIntent.ClearError)
            onNavigateToSignup()
        }) {
            Text("Don't have an account? Create one")
        }
    }
}
