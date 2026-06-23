package com.personal.astrocore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroPrimary
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import com.personal.astrocore.ui.theme.AstroSurfaceHigh
import com.personal.astrocore.ui.theme.GlassPanel
import com.personal.astrocore.ui.theme.White05
import com.personal.astrocore.ui.theme.White10

@Composable
fun AstroHeader(
    modifier: Modifier = Modifier,
    showSystemChip: Boolean = false,
    showCaptureNav: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .border(width = 1.dp, color = White10)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Public,
                contentDescription = null,
                tint = AstroPrimary,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "ASTRO-CORE",
                style = MaterialTheme.typography.labelSmall,
                color = AstroPrimary,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showCaptureNav) {
                Row(modifier = Modifier.padding(end = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MonoLabel(text = "Calibration", color = AstroOnSurfaceVariant)
                    MonoLabel(text = "Sky_Map", color = AstroOnSurfaceVariant)
                    MonoLabel(text = "Observation", color = AstroSecondaryContainer)
                }
            } else if (showSystemChip) {
                Row(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(AstroSurfaceHigh)
                        .border(1.dp, White05, RoundedCornerShape(999.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(AstroSecondaryContainer)
                    )
                    Text(
                        text = "System: Optimal",
                        style = MaterialTheme.typography.labelSmall,
                        color = AstroOnSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = AstroOnSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(GlassPanel)
            .border(1.dp, White10, RoundedCornerShape(12.dp))
    ) {
        content()
    }
}

@Composable
fun MonoLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = AstroOnSurfaceVariant
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
    )
}

@Composable
fun StatusPill(
    text: String,
    modifier: Modifier = Modifier,
    showPulse: Boolean = false
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(AstroSurfaceHigh)
            .border(1.dp, White05, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showPulse) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(AstroSecondaryContainer)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = AstroSecondaryContainer,
            modifier = Modifier.padding(start = if (showPulse) 8.dp else 0.dp)
        )
    }
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MonoLabel(text = title)
        if (action != null) {
            Text(
                text = action.uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = AstroSecondaryContainer,
                textAlign = TextAlign.End
            )
        }
    }
}
