package com.abrarshakhi.denapawna.presentation.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abrarshakhi.denapawna.data.local.pref.UserPreferences
import com.abrarshakhi.denapawna.presentation.dashboard.DashboardScreen
import com.abrarshakhi.denapawna.presentation.dashboard.DashboardViewModel
import com.abrarshakhi.denapawna.presentation.insights.DayDetailScreen
import com.abrarshakhi.denapawna.presentation.insights.InsightsScreen
import com.abrarshakhi.denapawna.presentation.insights.InsightsViewModel
import com.abrarshakhi.denapawna.presentation.settings.SettingsScreen
import com.abrarshakhi.denapawna.presentation.settings.SettingsViewModel
import com.abrarshakhi.denapawna.security.BiometricAuthenticator

@Composable
fun AppNavigation(
    userPreferences: UserPreferences,
    biometricAuthenticator: BiometricAuthenticator
) {
    val navController = rememberNavController()

    val slideSpec = remember {
        tween<IntOffset>(
            durationMillis = 280,
            easing = FastOutSlowInEasing
        )
    }
    val fadeSpec = remember {
        tween<Float>(
            durationMillis = 220,
            easing = FastOutSlowInEasing
        )
    }

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = slideSpec
            ) + fadeIn(animationSpec = fadeSpec)
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = slideSpec
            ) + fadeOut(animationSpec = fadeSpec)
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it / 3 },
                animationSpec = slideSpec
            ) + fadeIn(animationSpec = fadeSpec)
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = slideSpec
            ) + fadeOut(animationSpec = fadeSpec)
        }
    ) {
        composable("dashboard") {

        }

        composable("insights") {

        }

        composable("day_detail/{timestamp}",
        ) {

        }

        composable("settings") {

        }
    }
}