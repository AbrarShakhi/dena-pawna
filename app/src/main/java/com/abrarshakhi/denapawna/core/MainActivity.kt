package com.abrarshakhi.denapawna.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.abrarshakhi.denapawna.core.navigation.AppNavigation
import com.abrarshakhi.denapawna.core.ui.theme.DenaPawnaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { DenaPawnaTheme { AppNavigation() } }
    }
}