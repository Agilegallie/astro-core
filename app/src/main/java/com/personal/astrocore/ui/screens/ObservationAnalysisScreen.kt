package com.personal.astrocore.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.personal.astrocore.data.MockData
import com.personal.astrocore.ui.components.GalaxyBackground
import com.personal.astrocore.ui.components.GlassCard
import com.personal.astrocore.ui.components.MonoLabel
import com.personal.astrocore.ui.components.StatusPill
import com.personal.astrocore.ui.theme.AstroBackgroundLowest
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroPrimary
import com.personal.astrocore.ui.theme.AstroPrimaryContainer
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import com.personal.astrocore.ui.theme.AstroSurfaceHigh
import com.personal.astrocore.ui.theme.AstroSurfaceLow
import com.personal.astrocore.ui.theme.White10
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class UplinkState { Idle, Uploading, Transmitted }

@Composable
fun ObservationAnalysisScreen(
    onSubmitComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var observerNotes by rememberSaveable { mutableStateOf("") }
    var uplinkState by rememberSaveable { mutableStateOf(UplinkState.Idle) }
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(horizontal = 16.dp)
            .padding(bottom = 96.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                MonoLabel(text = "Verification Protocol v4.2", color = AstroSecondaryContainer)
                Text(
                    text = "Observation Analysis",
                    style = MaterialTheme.typography.displayLarge,
                    color = AstroPrimary
                )
            }
            StatusPill(text = "SYNCING_DATA", showPulse = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Box {
                GalaxyBackground(modifier = Modifier.height(220.dp))
                Column(modifier = Modifier.padding(16.dp)) {
                    MonoLabel(text = "CAM_REF_092 // DEEP_SKY_ARRAY", color = AstroSecondaryContainer)
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatChip("MAGNITUDE", "18.42")
                    StatChip("NOISE REDUCTION", "99.8%")
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.ZoomIn, contentDescription = null, tint = Color.White)
                    Icon(Icons.Default.Apps, contentDescription = null, tint = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            AnalysisStatCard("STARS DETECTED", MockData.observationStats.starsDetected, highlighted = true, modifier = Modifier.weight(1f))
            AnalysisStatCard("EXPOSURE TIME", MockData.observationStats.exposureTime, modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            AnalysisStatCard("APERTURE", MockData.observationStats.aperture, modifier = Modifier.weight(1f))
            AnalysisStatCard("ISO_LEVEL", MockData.observationStats.isoLevel, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("OBSERVATION REPORT", style = MaterialTheme.typography.headlineSmall)
                }
                Spacer(modifier = Modifier.height(16.dp))
                ReadOnlyField("TIMESTAMP (UTC)", "2024-05-24 T22:14:05.182", locked = true)
                Spacer(modifier = Modifier.height(12.dp))
                ReadOnlyField("ORBITAL SECTOR", "SQ-94 / PHI-7")
                Spacer(modifier = Modifier.height(12.dp))
                MonoLabel(text = "PRECISE GPS COORDINATES")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ReadOnlyField("LAT", "44.2443° N", modifier = Modifier.weight(1f))
                    ReadOnlyField("LON", "7.7691° E", modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(12.dp))
                MonoLabel(text = "TELESCOPE METADATA")
                GlassCard(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        MetadataRow("MOUNT_TYPE:", "EQUATORIAL_GO_TO")
                        MetadataRow("FL_DISTANCE:", "2350MM")
                        MetadataRow("SENSOR:", "CMOS_FULL_FRAME_45MP")
                        MetadataRow("FIRMWARE:", "CORE-OS v9.1.4")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                MonoLabel(text = "OBSERVER NOTES")
                OutlinedTextField(
                    value = observerNotes,
                    onValueChange = { observerNotes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .height(120.dp),
                    placeholder = { Text("Record visual anomalies or atmospheric interference...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AstroSecondaryContainer,
                        unfocusedBorderColor = White10,
                        focusedContainerColor = AstroBackgroundLowest,
                        unfocusedContainerColor = AstroBackgroundLowest
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (uplinkState != UplinkState.Idle) return@Button
                        uplinkState = UplinkState.Uploading
                        scope.launch {
                            delay(2500)
                            uplinkState = UplinkState.Transmitted
                            delay(400)
                            onSubmitComplete()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    enabled = uplinkState == UplinkState.Idle,
                    colors = ButtonDefaults.buttonColors(containerColor = AstroPrimaryContainer)
                ) {
                    when (uplinkState) {
                        UplinkState.Idle -> {
                            Text("SUBMIT TO NASA")
                            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.padding(start = 8.dp))
                        }
                        UplinkState.Uploading -> {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.padding(end = 8.dp))
                            Text("Establishing Uplink...")
                        }
                        UplinkState.Transmitted -> Text("TRANSMITTED", color = AstroSecondaryContainer)
                    }
                }
                Text(
                    text = "ENCRYPTED UPLINK SECURED via RSA-4096",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.3f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun AnalysisStatCard(label: String, value: String, highlighted: Boolean = false, modifier: Modifier = Modifier) {
    GlassCard(
        modifier = modifier.then(
            if (highlighted) Modifier.border(2.dp, AstroSecondaryContainer, RoundedCornerShape(12.dp))
            else Modifier
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            MonoLabel(text = label)
            Text(value, style = MaterialTheme.typography.headlineSmall, color = if (highlighted) AstroSecondaryContainer else Color.White)
        }
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.5f))
        Text(value, style = MaterialTheme.typography.labelLarge, color = Color.White)
    }
}

@Composable
private fun ReadOnlyField(label: String, value: String, locked: Boolean = false, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        MonoLabel(text = label)
        GlassCard(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(value, style = MaterialTheme.typography.labelLarge)
                if (locked) Icon(Icons.Default.Lock, contentDescription = null, tint = AstroSecondaryContainer)
            }
        }
    }
}

@Composable
private fun MetadataRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.labelLarge, color = AstroOnSurfaceVariant)
        Text(value, style = MaterialTheme.typography.labelLarge)
    }
}
