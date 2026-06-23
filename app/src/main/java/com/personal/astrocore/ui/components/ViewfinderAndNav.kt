package com.personal.astrocore.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.personal.astrocore.navigation.AstroTab
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import com.personal.astrocore.ui.theme.AstroSurface
import com.personal.astrocore.ui.theme.White10

@Composable
fun AstroBottomBar(
    selectedTab: AstroTab,
    onTabSelected: (AstroTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(AstroSurface.copy(alpha = 0.9f))
            .border(width = 1.dp, color = White10)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AstroTab.entries.forEach { tab ->
            val selected = tab == selectedTab
            val tint = if (selected) AstroSecondaryContainer else AstroOnSurfaceVariant
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (selected) AstroSecondaryContainer.copy(alpha = 0.2f)
                        else Color.Transparent
                    )
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = tab.icon,
                    contentDescription = tab.label,
                    tint = tint
                )
                Text(
                    text = tab.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = tint,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ViewfinderHud(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(256.dp)) {
            val cyan = Color(0x3300E3FD)
            val stroke = Stroke(width = 2f)
            drawCircle(color = cyan, radius = size.minDimension / 2, style = stroke)
            drawCircle(color = Color(0x6600E3FD), radius = size.minDimension * 0.375f, style = stroke)
            drawLine(cyan, Offset(0f, center.y), Offset(size.width, center.y), strokeWidth = 1f)
            drawLine(cyan, Offset(center.x, 0f), Offset(center.x, size.height), strokeWidth = 1f)
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .border(1.dp, AstroSecondaryContainer, RoundedCornerShape(2.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(AstroSecondaryContainer)
            )
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val bracket = 32.dp.toPx()
            val insetX = size.width * 0.25f
            val insetY = size.height * 0.25f
            val color = AstroSecondaryContainer
            val stroke = Stroke(width = 4f, cap = StrokeCap.Square)
            drawLine(color, Offset(insetX, insetY), Offset(insetX + bracket, insetY), strokeWidth = 4f)
            drawLine(color, Offset(insetX, insetY), Offset(insetX, insetY + bracket), strokeWidth = 4f)
            drawLine(color, Offset(size.width - insetX, insetY), Offset(size.width - insetX - bracket, insetY), strokeWidth = 4f)
            drawLine(color, Offset(size.width - insetX, insetY), Offset(size.width - insetX, insetY + bracket), strokeWidth = 4f)
            drawLine(color, Offset(insetX, size.height - insetY), Offset(insetX + bracket, size.height - insetY), strokeWidth = 4f)
            drawLine(color, Offset(insetX, size.height - insetY), Offset(insetX, size.height - insetY - bracket), strokeWidth = 4f)
            drawLine(color, Offset(size.width - insetX, size.height - insetY), Offset(size.width - insetX - bracket, size.height - insetY), strokeWidth = 4f)
            drawLine(color, Offset(size.width - insetX, size.height - insetY), Offset(size.width - insetX, size.height - insetY - bracket), strokeWidth = 4f)
        }
    }
}

@Composable
fun TransmissionStepper(
    steps: List<com.personal.astrocore.model.PipelineStep>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val x = 19.dp.toPx()
            drawLine(
                color = White10,
                start = Offset(x, 20.dp.toPx()),
                end = Offset(x, size.height - 20.dp.toPx()),
                strokeWidth = 2f
            )
            drawLine(
                color = AstroSecondaryContainer,
                start = Offset(x, 20.dp.toPx()),
                end = Offset(x, 20.dp.toPx() + (size.height - 40.dp.toPx()) * 0.66f),
                strokeWidth = 2f
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (index < steps.lastIndex) 24.dp else 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                if (step.isComplete) AstroSecondaryContainer
                                else Color.Transparent
                            )
                            .border(
                                2.dp,
                                if (step.isComplete) AstroSecondaryContainer else White10,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (step.isComplete) "✓" else "…",
                            color = if (step.isComplete) Color.Black else AstroOnSurfaceVariant
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        MonoLabel(
                            text = "STEP ${step.stepNumber}",
                            color = if (step.isComplete) AstroSecondaryContainer else AstroOnSurfaceVariant
                        )
                        Text(
                            text = step.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = if (step.isComplete) 1f else 0.4f
                            )
                        )
                        Text(
                            text = step.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AstroOnSurfaceVariant.copy(alpha = if (step.isComplete) 1f else 0.4f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StarfieldBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0B0E14))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width.toInt().coerceAtLeast(1)
            val height = size.height.toInt().coerceAtLeast(1)
            repeat(80) { i ->
                val x = (i * 97 % width).toFloat()
                val y = (i * 53 % height).toFloat()
                drawCircle(
                    color = Color.White.copy(alpha = 0.2f + (i % 5) * 0.1f),
                    radius = 1f + (i % 3),
                    center = Offset(x, y)
                )
            }
        }
    }
}

@Composable
fun SpaceGradientCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A2744),
                        Color(0xFF0B0E14),
                        Color(0xFF2D1B3D)
                    )
                )
            )
    )
}
