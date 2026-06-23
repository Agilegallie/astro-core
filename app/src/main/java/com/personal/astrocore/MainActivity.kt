package com.personal.astrocore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.personal.astrocore.navigation.AstroRoute
import com.personal.astrocore.navigation.AstroTab
import com.personal.astrocore.navigation.route
import com.personal.astrocore.navigation.routeToTab
import com.personal.astrocore.ui.components.AstroBottomBar
import com.personal.astrocore.ui.components.AstroHeader
import com.personal.astrocore.ui.screens.AsteroidDaySplashScreen
import com.personal.astrocore.ui.screens.CaptureScreen
import com.personal.astrocore.ui.screens.HomeScreen
import com.personal.astrocore.ui.screens.ObservationAnalysisScreen
import com.personal.astrocore.ui.screens.ProfilePlaceholderScreen
import com.personal.astrocore.ui.screens.ReportTransmittedScreen
import com.personal.astrocore.ui.theme.AstroCoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AstroCoreTheme {
                AstroApp()
            }
        }
    }
}

@Composable
fun AstroApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedTab = routeToTab(currentRoute)
    val isSplash = currentRoute == AstroRoute.Splash
    val showSystemChip = currentRoute == AstroRoute.Home
    val showCaptureNav = currentRoute == AstroRoute.Capture || currentRoute == AstroRoute.Analysis

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (isSplash) {
            AstroNavHost(navController = navController)
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                AstroHeader(showSystemChip = showSystemChip, showCaptureNav = showCaptureNav)
                Box(modifier = Modifier.weight(1f)) {
                    AstroNavHost(navController = navController)
                }
                AstroBottomBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        navController.navigate(tab.route()) {
                            popUpTo(AstroRoute.Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AstroNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AstroRoute.Splash,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp)
    ) {
        composable(AstroRoute.Splash) {
            AsteroidDaySplashScreen(
                onEnter = {
                    navController.navigate(AstroRoute.Home) {
                        popUpTo(AstroRoute.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable(AstroRoute.Home) {
            HomeScreen(
                onQuickCapture = {
                    navController.navigate(AstroRoute.Capture)
                }
            )
        }
        composable(AstroRoute.Capture) {
            CaptureScreen(
                onCapture = {
                    navController.navigate(AstroRoute.Analysis)
                }
            )
        }
        composable(AstroRoute.Analysis) {
            ObservationAnalysisScreen(
                onSubmitComplete = {
                    navController.navigate(AstroRoute.ReportSuccess) {
                        popUpTo(AstroRoute.Home) { inclusive = false }
                    }
                }
            )
        }
        composable(AstroRoute.ReportSuccess) {
            ReportTransmittedScreen(
                onReturnHome = {
                    navController.navigate(AstroRoute.Home) {
                        popUpTo(AstroRoute.Home) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(AstroRoute.Profile) {
            ProfilePlaceholderScreen()
        }
    }
}
