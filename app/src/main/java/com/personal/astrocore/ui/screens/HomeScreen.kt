package com.personal.astrocore.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.personal.astrocore.data.MockData
import com.personal.astrocore.model.DangerLevel
import com.personal.astrocore.ui.components.GlassCard
import com.personal.astrocore.ui.components.MonoLabel
import com.personal.astrocore.ui.components.NeoThumbnail
import com.personal.astrocore.ui.components.SectionTitle
import com.personal.astrocore.ui.components.SpaceGradientCard
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroPrimary
import com.personal.astrocore.ui.theme.AstroPrimaryContainer
import com.personal.astrocore.ui.theme.AstroSecondary
import com.personal.astrocore.ui.theme.AstroSecondaryContainer

@Composable
fun HomeScreen(
    onQuickCapture: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    val scanTransition = rememberInfiniteTransition(label = "scan")
    val scanY by scanTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Restart),
        label = "scanY"
    )
    val pingTransition = rememberInfiniteTransition(label = "ping")
    val pingScale by pingTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Restart),
        label = "pingScale"
    )
    val pingAlpha by pingTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Restart),
        label = "pingAlpha"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp)
            .padding(bottom = 96.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .offset(y = (scanY * 180).dp)
                        .background(AstroSecondaryContainer.copy(alpha = 0.2f))
                )
                Column(modifier = Modifier.padding(24.dp)) {
                    MonoLabel(text = "Network Status", color = AstroSecondary)
                    Text(
                        text = "Sky Sentinel: Safe",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = MockData.networkStatusBody,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AstroOnSurfaceVariant
                    )
                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatTile(label = "Threat Level", value = "0.02%")
                        StatTile(label = "Sensors", value = "98.4%")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MonoLabel(text = "Local Observation")
                    Text(
                        text = "40.7128° N, 74.0060° W",
                        style = MaterialTheme.typography.labelLarge,
                        color = AstroSecondaryContainer
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    SpaceGradientCard(modifier = Modifier.fillMaxSize())
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .scale(pingScale)
                                .alpha(pingAlpha)
                                .border(1.dp, AstroSecondaryContainer.copy(alpha = 0.5f), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .border(1.dp, AstroSecondaryContainer.copy(alpha = 0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(AstroSecondaryContainer)
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Visibility Index", style = MaterialTheme.typography.labelLarge, color = AstroOnSurfaceVariant)
                    Text("Excellent (84%)", style = MaterialTheme.typography.labelLarge, color = AstroSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onQuickCapture,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AstroPrimaryContainer)
        ) {
            androidx.compose.material3.Icon(Icons.Default.PhotoCamera, contentDescription = null)
            Text(
                text = "QUICK CAPTURE NEO",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle(title = "Recent Global Sightings", action = "View All Logs")
        Spacer(modifier = Modifier.height(12.dp))

        MockData.sightings.forEach { sighting ->
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    NeoThumbnail(modifier = Modifier.size(96.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(sighting.id, style = MaterialTheme.typography.labelLarge)
                            Text(
                                "Velocity: ${sighting.velocityKmS}",
                                style = MaterialTheme.typography.labelSmall,
                                color = AstroOnSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(width = 12.dp, height = 4.dp)
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(
                                        if (sighting.dangerLevel == DangerLevel.LOW) AstroSecondaryContainer
                                        else AstroPrimaryContainer
                                    )
                            )
                            Text(
                                text = sighting.dangerLabel.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (sighting.dangerLevel == DangerLevel.LOW) AstroSecondary else AstroPrimary,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatTile(label: String, value: String) {
    GlassCard {
        Column(
            modifier = Modifier.padding(16.dp).size(width = 112.dp, height = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MonoLabel(text = label, color = AstroOnSurfaceVariant)
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = AstroSecondaryContainer,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
