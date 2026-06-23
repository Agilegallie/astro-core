package com.personal.astrocore.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.personal.astrocore.data.MockData
import com.personal.astrocore.ui.components.GlassCard
import com.personal.astrocore.ui.components.MonoLabel
import com.personal.astrocore.ui.components.StatusPill
import com.personal.astrocore.ui.components.TransmissionStepper
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroPrimary
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import com.personal.astrocore.ui.theme.White05
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReportTransmittedScreen(
    onReturnHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    var copied by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scroll = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .size(384.dp)
                .align(Alignment.TopEnd)
                .offset(x = 96.dp, y = 80.dp)
                .clip(CircleShape)
                .background(AstroPrimary.copy(alpha = 0.05f))
        )
        Box(
            modifier = Modifier
                .size(384.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-96).dp, y = (-80).dp)
                .clip(CircleShape)
                .background(AstroSecondaryContainer.copy(alpha = 0.05f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(horizontal = 16.dp)
                .padding(bottom = 96.dp)
        ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(AstroSecondaryContainer.copy(alpha = 0.1f))
                    .border(2.dp, AstroSecondaryContainer.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = AstroSecondaryContainer,
                    modifier = Modifier.size(48.dp)
                )
            }
            Text(
                text = "Report Transmitted",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
            Text(
                text = "Your deep space telemetry packet has been successfully uplinked. Propagation to terrestrial data centers is currently in progress.",
                style = MaterialTheme.typography.bodyMedium,
                color = AstroOnSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AstroSecondaryContainer.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MonoLabel(text = "Transmission Pipeline", color = AstroSecondaryContainer)
                    StatusPill(text = "LIVE LINK ACTIVE", showPulse = true)
                }
                Spacer(modifier = Modifier.height(24.dp))
                TransmissionStepper(steps = MockData.pipelineSteps)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                MonoLabel(text = "Reference ID")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(White05)
                        .border(1.dp, White05, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(MockData.REFERENCE_ID, style = MaterialTheme.typography.labelLarge, color = AstroPrimary)
                    Icon(
                        imageVector = if (copied) Icons.Default.CheckCircle else Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = if (copied) AstroSecondaryContainer else AstroOnSurfaceVariant,
                        modifier = Modifier.clickable {
                            val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            manager.setPrimaryClip(ClipData.newPlainText("Reference ID", MockData.REFERENCE_ID))
                            copied = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                copied = false
                            }
                        }
                    )
                }
                Text(
                    text = "Use this ID for querying verification status at the Deep Space Operations center.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AstroOnSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp)) {
                MonoLabel(text = "Payload Specs")
                MockData.payloadSpecs.forEach { spec ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(spec.label, style = MaterialTheme.typography.bodyMedium, color = AstroOnSurfaceVariant)
                        Text(spec.value, style = MaterialTheme.typography.labelLarge)
                    }
                }
                Button(
                    onClick = onReturnHome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AstroPrimary)
                ) {
                    Text("RETURN TO DASHBOARD", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
        }
    }
}

@Composable
fun ProfilePlaceholderScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Profile — coming in v2", style = MaterialTheme.typography.bodyLarge, color = AstroOnSurfaceVariant)
    }
}
