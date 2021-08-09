package nl.trivento.co2backend.domain

data class Room(val name: String, var condition: Condition?, val sensors: List<String> = emptyList())
