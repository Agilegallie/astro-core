package com.personal.astrocore.data

import com.personal.astrocore.model.DangerLevel
import com.personal.astrocore.model.NeoSighting
import com.personal.astrocore.model.ObservationStats
import com.personal.astrocore.model.PayloadSpec
import com.personal.astrocore.model.PipelineStep

object MockData {
    const val REFERENCE_ID = "TX-AX40-9821-Q"

    val networkStatusBody =
        "Global monitoring active. 14,209 NEOs currently tracked within sector 7-G. " +
            "No immediate collision threats detected in the 48-hour window."

    val sightings = listOf(
        NeoSighting("AP-2024-X1", "24.5 km/s", DangerLevel.LOW, "Low Danger"),
        NeoSighting("NEO-982-K", "18.2 km/s", DangerLevel.LOW, "Low Danger"),
        NeoSighting("OBS-77-ALERT", "41.9 km/s", DangerLevel.MODERATE, "Moderate Alert")
    )

    val observationStats = ObservationStats(
        starsDetected = "14,802",
        exposureTime = "3,600s",
        aperture = "f/2.8",
        isoLevel = "800"
    )

    val pipelineSteps = listOf(
        PipelineStep("01", "Local Capture", "Sensor data indexed and validated at source.", isComplete = true),
        PipelineStep("02", "Data Packaging", "Encrypted into high-density celestial packets.", isComplete = true),
        PipelineStep(
            "03",
            "Sent to NASA (JPL)",
            "Relayed via Deep Space Network. Transit latency: 0.8s.",
            isComplete = true,
            isActive = true
        ),
        PipelineStep(
            "04",
            "Awaiting Verification",
            "Terrestrial server validation and catalog entry.",
            isComplete = false
        )
    )

    val payloadSpecs = listOf(
        PayloadSpec("File Size", "1.28 GB"),
        PayloadSpec("Encryption", "AES-512-Q"),
        PayloadSpec("Bandwidth", "KA-BAND")
    )
}
