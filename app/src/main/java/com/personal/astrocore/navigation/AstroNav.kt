package com.personal.astrocore.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class AstroTab(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Dashboard),
    Capture("Capture", Icons.Default.PhotoCamera),
    Reports("Reports", Icons.Default.Analytics),
    Profile("Profile", Icons.Default.Person)
}

object AstroRoute {
    const val Splash = "splash"
    const val Home = "home"
    const val Capture = "capture"
    const val Analysis = "analysis"
    const val ReportSuccess = "report_success"
    const val Profile = "profile"
}

fun AstroTab.route(): String = when (this) {
    AstroTab.Home -> AstroRoute.Home
    AstroTab.Capture -> AstroRoute.Capture
    AstroTab.Reports -> AstroRoute.ReportSuccess
    AstroTab.Profile -> AstroRoute.Profile
}

fun routeToTab(route: String?): AstroTab = when (route) {
    AstroRoute.Capture, AstroRoute.Analysis -> AstroTab.Capture
    AstroRoute.ReportSuccess -> AstroTab.Reports
    AstroRoute.Profile -> AstroTab.Profile
    else -> AstroTab.Home
}
