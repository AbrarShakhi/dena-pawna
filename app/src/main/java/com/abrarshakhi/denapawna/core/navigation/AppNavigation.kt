package com.abrarshakhi.denapawna.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.features.presentation.details.DetailScreen
import com.abrarshakhi.denapawna.features.presentation.details.DetailsViewModel
import com.abrarshakhi.denapawna.features.presentation.home.HomeScreen
import com.abrarshakhi.denapawna.features.presentation.home.HomeViewModel
import com.abrarshakhi.denapawna.features.presentation.settings.SettingsScreen
import com.abrarshakhi.denapawna.features.presentation.settings.SettingsViewModel

@Composable
fun AppNavigation() {
    val applicationContext = LocalContext.current.applicationContext

    val backStack = rememberNavBackStack(AppNavKey.Home)

    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(applicationContext))

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
    ) { key ->
        when (key) {
            is AppNavKey.Home -> {
                NavEntry(key = key) {
                    val homeState by homeViewModel.state.collectAsStateWithLifecycle()
                    HomeScreen(
                        state = homeState,
                        effect = homeViewModel.effect,
                        onIntent = homeViewModel::onIntent,
                        onPersonClick = { personId -> backStack.add(AppNavKey.Detail(personId)) },
                        onSettingsClick = { backStack.add(AppNavKey.Settings) },
                    )
                }
            }

            is AppNavKey.Detail -> {
                NavEntry(key = key) {
                    val detailsViewModel: DetailsViewModel = viewModel(
                        key = key.personId.toString(),
                        factory = DetailsViewModel.Factory(applicationContext, key.personId),
                    )
                    val detailsState by detailsViewModel.state.collectAsStateWithLifecycle()
                    DetailScreen(
                        state = detailsState,
                        effect = detailsViewModel.effect,
                        onIntent = detailsViewModel::onIntent,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
            }

            is AppNavKey.Settings -> {
                NavEntry(key = key) {
                    val settingsViewModel: SettingsViewModel = viewModel(
                        factory = SettingsViewModel.Factory(
                            (applicationContext as DenaPawna).themePreferences
                        )
                    )
                    val settingsState by settingsViewModel.state.collectAsStateWithLifecycle()
                    SettingsScreen(
                        state = settingsState,
                        onIntent = settingsViewModel::onIntent,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
            }

            else -> throw RuntimeException("Invalid NavKey: $key")
        }
    }
}
