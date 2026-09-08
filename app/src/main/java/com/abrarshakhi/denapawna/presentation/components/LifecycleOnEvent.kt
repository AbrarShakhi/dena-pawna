package com.abrarshakhi.denapawna.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun LifecycleOnEvent(
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val onStartState by rememberUpdatedState(onStart)
    val onResumeState by rememberUpdatedState(onResume)
    val onPauseState by rememberUpdatedState(onPause)
    val onStopState by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStartState?.invoke()
                Lifecycle.Event.ON_RESUME -> onResumeState?.invoke()
                Lifecycle.Event.ON_PAUSE -> onPauseState?.invoke()
                Lifecycle.Event.ON_STOP -> onStopState?.invoke()
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}