package nl.trivento.co2backend.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Condition(val co2: Double? = null)

