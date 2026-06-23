package com.personal.astrocore.model

enum class DangerLevel { LOW, MODERATE }

data class NeoSighting(
    val id: String,
    val velocityKmS: String,
    val dangerLevel: DangerLevel,
    val dangerLabel: String
)

data class PipelineStep(
    val stepNumber: String,
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val isActive: Boolean = false
)

data class PayloadSpec(
    val label: String,
    val value: String
)

data class ObservationStats(
    val starsDetected: String,
    val exposureTime: String,
    val aperture: String,
    val isoLevel: String
)
