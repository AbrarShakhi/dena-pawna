package com.abrarshakhi.denapawna.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.abrarshakhi.denapawna.data.local.pref.UserPreferences
import com.abrarshakhi.denapawna.presentation.components.LifecycleOnEvent
import com.abrarshakhi.denapawna.presentation.components.LockScreen
import com.abrarshakhi.denapawna.presentation.navigation.AppNavigation
import com.abrarshakhi.denapawna.presentation.theme.DenaPawnaTheme
import com.abrarshakhi.denapawna.security.BiometricAuthenticator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var biometricAuthenticator: BiometricAuthenticator

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.preferredDisplayModeId = 0
        enableEdgeToEdge()
        setContent {
            val settings by userPreferences.settingsFlow.collectAsState(initial = null)
            val scope = rememberCoroutineScope()

            settings?.let { userSettings ->
                DenaPawnaTheme(theme = userSettings.theme) {
                    val currentSettings by rememberUpdatedState(userSettings)
                    var isAuthenticated by remember {
                        mutableStateOf(!userSettings.isBiometricEnabled || !biometricAuthenticator.isBiometricAvailable())
                    }
                    var resumeTrigger by remember { mutableIntStateOf(0) }

                    LifecycleOnEvent(
                        onStart = {
                            resumeTrigger++
                            val settingsSnapshot = currentSettings
                            val shouldLock = settingsSnapshot.isBiometricEnabled &&
                                    biometricAuthenticator.isBiometricAvailable()

                            if (shouldLock) {
                                val timeDiff =
                                    System.currentTimeMillis() - settingsSnapshot.lastStopTime
                                val isGracePeriodOver =
                                    timeDiff >= settingsSnapshot.autoLockTimeout

                                if (isAuthenticated && isGracePeriodOver) {
                                    isAuthenticated = false
                                }
                            } else {
                                isAuthenticated = true
                            }
                        },
                        onStop = {
                            scope.launch {
                                userPreferences.setLastStopTime(System.currentTimeMillis())
                            }
                        },
                        lifecycleOwner = LocalLifecycleOwner.current
                    )

                    val permissionLauncher = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { }

                    LaunchedEffect(isAuthenticated, resumeTrigger) {
                        if (!isAuthenticated) {
                            if (userSettings.isBiometricEnabled && biometricAuthenticator.isBiometricAvailable()) {
                                biometricAuthenticator.authenticate(
                                    activity = this@MainActivity,
                                    onSuccess = { isAuthenticated = true },
                                    onError = { }
                                )
                            } else {
                                isAuthenticated = true
                            }
                        }
                    }

                    LaunchedEffect(userSettings.isBiometricEnabled) {
                        if (!userSettings.isBiometricEnabled) {
                            isAuthenticated = true
                        }
                    }

                    LaunchedEffect(Unit) {
                        permissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
                    }

                    Box(modifier = Modifier.fillMaxSize()) {
                        AppNavigation(
                            userPreferences,
                            biometricAuthenticator
                        )

                        if (!isAuthenticated) {
                            LockScreen(
                                onUnlockClick = {
                                    resumeTrigger++
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}