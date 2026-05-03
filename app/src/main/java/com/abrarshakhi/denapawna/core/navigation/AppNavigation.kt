package com.abrarshakhi.denapawna.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.abrarshakhi.denapawna.core.DenaPawna
import com.abrarshakhi.denapawna.features.presentation.account.AccountScreen
import com.abrarshakhi.denapawna.features.presentation.account.AccountViewModel
import com.abrarshakhi.denapawna.features.presentation.auth.AuthViewModel
import com.abrarshakhi.denapawna.features.presentation.auth.LoginScreen
import com.abrarshakhi.denapawna.features.presentation.auth.SignupScreen
import com.abrarshakhi.denapawna.features.presentation.details.DetailScreen
import com.abrarshakhi.denapawna.features.presentation.details.DetailsViewModel
import com.abrarshakhi.denapawna.features.presentation.home.HomeScreen
import com.abrarshakhi.denapawna.features.presentation.home.HomeViewModel

@Composable
fun AppNavigation() {
    val applicationContext = LocalContext.current.applicationContext
    val app = applicationContext as DenaPawna

    // Determine initial destination synchronously from persisted auth state
    val startKey = if (app.getCurrentUserUseCase.getCurrentUser().value != null) {
        AppNavKey.Home
    } else {
        AppNavKey.Login
    }

    val backStack = rememberNavBackStack(startKey)

    // Observe auth state changes — handles login success and logout
    val currentUser by app.getCurrentUserUseCase.getCurrentUser().collectAsStateWithLifecycle()
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            // User just logged in: clear auth screens, go to Home
            val hasAuthScreen = backStack.any { it is AppNavKey.Login || it is AppNavKey.Signup }
            if (hasAuthScreen) {
                backStack.removeAll { it is AppNavKey.Login || it is AppNavKey.Signup }
                if (backStack.none { it is AppNavKey.Home }) {
                    backStack.add(AppNavKey.Home)
                }
            }
        } else {
            // User logged out: clear everything, go to Login
            if (backStack.none { it is AppNavKey.Login }) {
                backStack.clear()
                backStack.add(AppNavKey.Login)
            }
        }
    }

    // ViewModels shared across related screens
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory(applicationContext))
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(applicationContext))

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
    ) { key ->
        when (key) {
            is AppNavKey.Login -> {
                NavEntry(key = key) {
                    val authState by authViewModel.state.collectAsStateWithLifecycle()
                    LoginScreen(
                        state = authState,
                        onIntent = authViewModel::onIntent,
                        onNavigateToSignup = { backStack.add(AppNavKey.Signup) },
                    )
                }
            }

            is AppNavKey.Signup -> {
                NavEntry(key = key) {
                    val authState by authViewModel.state.collectAsStateWithLifecycle()
                    SignupScreen(
                        state = authState,
                        onIntent = authViewModel::onIntent,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
            }

            is AppNavKey.Home -> {
                NavEntry(key = key) {
                    val homeState by homeViewModel.state.collectAsStateWithLifecycle()
                    HomeScreen(
                        state = homeState,
                        effect = homeViewModel.effect,
                        onIntent = homeViewModel::onIntent,
                        onPersonClick = { personId -> backStack.add(AppNavKey.Detail(personId)) },
                        onAccountClick = { backStack.add(AppNavKey.Account) },
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

            is AppNavKey.Account -> {
                NavEntry(key = key) {
                    val accountViewModel: AccountViewModel =
                        viewModel(factory = AccountViewModel.Factory(applicationContext))
                    val accountState by accountViewModel.state.collectAsStateWithLifecycle()
                    AccountScreen(
                        state = accountState,
                        effect = accountViewModel.effect,
                        onIntent = accountViewModel::onIntent,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
            }

            else -> throw RuntimeException("Invalid NavKey: $key")
        }
    }
}
