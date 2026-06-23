package com.personal.astrocore.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.personal.astrocore.ui.components.GlassCard
import com.personal.astrocore.ui.components.MilkyWayBackground
import com.personal.astrocore.ui.components.MonoLabel
import com.personal.astrocore.ui.theme.AstroOnSurfaceVariant
import com.personal.astrocore.ui.theme.AstroPrimary
import com.personal.astrocore.ui.theme.AstroPrimaryContainer
import com.personal.astrocore.ui.theme.AstroSecondary
import com.personal.astrocore.ui.theme.AstroSecondaryContainer
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun AsteroidDaySplashScreen(
    onEnter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stars = remember {
        List(80) {
            Star(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                radius = Random.nextFloat() * 1.8f + 0.4f,
                phase = Random.nextFloat()
            )
        }
    }

    val transition = rememberInfiniteTransition(label = "splash")
    val asteroidFloat by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(6000, easing = LinearEasing), RepeatMode.Reverse),
        label = "float"
    )
    val asteroidSpin by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(24000, easing = LinearEasing), RepeatMode.Restart),
        label = "spin"
    )
    val glowPulse by transition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing), RepeatMode.Reverse),
        label = "glow"
    )

    Box(modifier = modifier.fillMaxSize()) {
        MilkyWayBackground()

        Canvas(modifier = Modifier.fillMaxSize()) {
            stars.forEach { star ->
                val twinkle = 0.35f + 0.65f * ((sin(star.phase * 6.28f + asteroidSpin * 0.02f) + 1f) / 2f)
                drawCircle(
                    color = Color.White.copy(alpha = twinkle * 0.85f),
                    radius = star.radius,
                    center = Offset(star.x * size.width, star.y * size.height)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(320.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-40).dp)
                .alpha(glowPulse * 0.15f)
                .clip(CircleShape)
                .background(AstroSecondaryContainer)
        )
        Box(
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = 40.dp)
                .alpha(glowPulse * 0.1f)
                .clip(CircleShape)
                .background(AstroPrimary)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 56.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Public,
                        contentDescription = null,
                        tint = AstroPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "ASTRO-CORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = AstroPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = (asteroidFloat * 12 - 6).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .alpha(glowPulse * 0.25f)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        AstroSecondaryContainer.copy(alpha = 0.5f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    AsteroidGraphic(
                        modifier = Modifier
                            .size(120.dp)
                            .rotate(asteroidSpin)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                MonoLabel(text = "World Asteroid Day", color = AstroSecondary)
                Text(
                    text = "30 June",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Look up. Track NEOs. Protect our planet.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Join citizen scientists worldwide in observing near-Earth objects — one of the UN-recognised goals of Asteroid Day.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AstroOnSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onEnter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .scale(0.98f + glowPulse * 0.02f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AstroPrimaryContainer)
                ) {
                    Text(
                        text = "ENTER",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Text(
                    text = "asteroidday.org",
                    style = MaterialTheme.typography.labelSmall,
                    color = AstroSecondaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun AsteroidGraphic(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = size.minDimension * 0.38f

        val path = Path().apply {
            val points = 8
            for (i in 0 until points) {
                val angle = (i.toFloat() / points) * 360f
                val wobble = if (i % 2 == 0) 1f else 0.78f
                val rad = Math.toRadians(angle.toDouble())
                val x = cx + cos(rad).toFloat() * r * wobble
                val y = cy + sin(rad).toFloat() * r * wobble
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }

        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF8B7355),
                    Color(0xFF4A3F35),
                    Color(0xFF2A2520)
                ),
                start = Offset(cx - r, cy - r),
                end = Offset(cx + r, cy + r)
            )
        )

        rotate(degrees = 35f, pivot = Offset(cx, cy)) {
            drawCircle(
                color = Color(0xFF1A1510).copy(alpha = 0.5f),
                radius = r * 0.18f,
                center = Offset(cx - r * 0.25f, cy + r * 0.1f)
            )
            drawCircle(
                color = Color(0xFF1A1510).copy(alpha = 0.35f),
                radius = r * 0.12f,
                center = Offset(cx + r * 0.3f, cy - r * 0.2f)
            )
        }

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    AstroSecondaryContainer.copy(alpha = 0.15f),
                    Color.Transparent
                ),
                center = Offset(cx - r * 0.3f, cy - r * 0.3f),
                radius = r * 0.6f
            ),
            radius = r * 0.6f,
            center = Offset(cx - r * 0.3f, cy - r * 0.3f)
        )
    }
}

private data class Star(
    val x: Float,
    val y: Float,
    val radius: Float,
    val phase: Float
)
