package nl.trivento.co2backend.domain

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Message(
    val name: String,
    val condition: Condition? = null,
)
