package nl.trivento.co2backend.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Condition(
    val co2: Double? = null,
    val temperature: Double? = null,
    val humidity: Double? = null,
    val lastUpdate: Instant? = null
)

