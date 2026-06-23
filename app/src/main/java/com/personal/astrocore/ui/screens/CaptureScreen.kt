package com.personal.astrocore.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.personal.astrocore.ui.components.GlassCard
import com.personal.astrocore.ui.components.MilkyWayBackground
import com.personal.astrocore.ui.components.MonoLabel
import com.personal.astrocore.ui.components.ViewfinderHud
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroOutline
import com.personal.astrocore.ui.theme.AstroPrimaryContainer
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import com.personal.astrocore.ui.theme.AstroSurfaceHighest
import com.personal.astrocore.ui.theme.White10
import kotlinx.coroutines.launch

@Composable
fun CaptureScreen(
    onCapture: () -> Unit,
    modifier: Modifier = Modifier
) {
    var telescopeSync by remember { mutableStateOf(true) }
    val flashAlpha = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val scanTransition = rememberInfiniteTransition(label = "captureScan")
    val scanY by scanTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart),
        label = "captureScanY"
    )

    Box(modifier = modifier.fillMaxSize()) {
        MilkyWayBackground(modifier = Modifier.alpha(0.6f))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (scanY * 800).dp - 400.dp)
                .background(AstroSecondaryContainer.copy(alpha = 0.05f))
        )
        ViewfinderHud()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 120.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TelemetryPanel(
                    title = "COORDINATES",
                    rows = listOf(
                        "Right Ascension" to "14h 29m 42.9s",
                        "Declination" to "-60° 50′ 02″"
                    )
                )
                TelemetryPanel(
                    title = "SYSTEM STATUS",
                    rows = listOf(
                        "Azimuth" to "124.52° ENE",
                        "Elevation" to "+42.18°"
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                GlassCard {
                    Text(
                        text = "HD 128620 | MAG: 0.1 | DIST: 4.37 LY",
                        style = MaterialTheme.typography.labelLarge,
                        color = AstroSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                GlassCard(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MonoLabel(text = "Telescope Sync")
                            Switch(
                                checked = telescopeSync,
                                onCheckedChange = { telescopeSync = it },
                                colors = SwitchDefaults.colors(
                                    checkedTrackColor = AstroSecondaryContainer,
                                    checkedThumbColor = Color.White
                                )
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("MOTOR TEMP", style = MaterialTheme.typography.labelLarge, color = AstroOutline)
                            Text("24.2°C", style = MaterialTheme.typography.labelLarge)
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(AstroSurfaceHighest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.45f)
                                    .height(4.dp)
                                    .background(AstroSecondaryContainer)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                GlassCard {
                    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        ExposureItem("ISO", "3200")
                        ExposureItem("SHUTTER", "1/500s")
                        ExposureItem("F-STOP", "f/8.0")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 88.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color.Black.copy(alpha = 0.4f))
                .border(1.dp, White10, RoundedCornerShape(999.dp))
                .padding(horizontal = 48.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DockButton(Icons.Default.Analytics, "DATA")
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(AstroPrimaryContainer)
                    .border(2.dp, Color.White.copy(alpha = 0.2f), CircleShape)
                    .clickable {
                        scope.launch {
                            flashAlpha.animateTo(1f, tween(50))
                            flashAlpha.animateTo(0f, tween(100))
                            onCapture()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.PhotoCamera, contentDescription = "Capture", tint = Color.White, modifier = Modifier.size(40.dp))
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(flashAlpha.value)
                        .background(Color.White)
                        .clip(CircleShape)
                )
            }
            DockButton(Icons.Default.Person, "BIO")
        }
    }
}

@Composable
private fun TelemetryPanel(title: String, rows: List<Pair<String, String>>) {
    GlassCard {
        Column(modifier = Modifier.padding(16.dp)) {
            MonoLabel(text = title, color = AstroSecondaryContainer)
            rows.forEach { (label, value) ->
                Text(label.uppercase(), style = MaterialTheme.typography.labelSmall, color = AstroOutline)
                Text(text = value, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(bottom = 4.dp))
            }
        }
    }
}

@Composable
private fun ExposureItem(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = AstroOutline)
        Text(value, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun DockButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = label, tint = AstroOnSurfaceVariant, modifier = Modifier.size(32.dp))
        Text(label, style = MaterialTheme.typography.labelSmall, color = AstroOnSurfaceVariant)
    }
}
