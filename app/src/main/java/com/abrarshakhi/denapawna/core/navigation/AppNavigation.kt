package com.abrarshakhi.denapawna.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.abrarshakhi.denapawna.features.presentation.details.DetailScreen
import com.abrarshakhi.denapawna.features.presentation.home.HomeScreen
import com.abrarshakhi.denapawna.features.presentation.home.HomeViewModel

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(AppNavKey.Home)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator())
    ) { key ->
        when (key) {
            is AppNavKey.Home -> {
                NavEntry(key = key) {
                    HomeScreen(
                        onPersonClick = { personId -> backStack.add(AppNavKey.Detail(personId)) },
                        viewModel = viewModel(factory = HomeViewModel.Factory(LocalContext.current.applicationContext))
                    )
                }
            }

            is AppNavKey.Detail -> {
                NavEntry(key = key) {
                    DetailScreen(key.personId)
                }
            }

            else -> throw RuntimeException("Invalid NavKey")
        }
    }
}