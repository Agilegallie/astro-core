package com.personal.astrocore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AstroColorScheme = darkColorScheme(
    background = AstroBackground,
    surface = AstroSurface,
    surfaceContainer = AstroSurface,
    surfaceContainerLow = AstroSurfaceLow,
    surfaceContainerHigh = AstroSurfaceHigh,
    surfaceContainerHighest = AstroSurfaceHighest,
    primary = AstroPrimary,
    onPrimary = AstroOnPrimary,
    primaryContainer = AstroPrimaryContainer,
    onPrimaryContainer = AstroOnPrimaryContainer,
    secondary = AstroSecondary,
    onSecondary = AstroOnSecondary,
    secondaryContainer = AstroSecondaryContainer,
    onSecondaryContainer = AstroOnSecondary,
    onSurface = AstroOnSurface,
    onSurfaceVariant = AstroOnSurfaceVariant,
    outline = AstroOutline,
    outlineVariant = AstroOutlineVariant
)

@Composable
fun AstroCoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AstroColorScheme,
        typography = AstroTypography,
        content = content
    )
}
