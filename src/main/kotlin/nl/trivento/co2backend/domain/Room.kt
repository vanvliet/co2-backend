package nl.trivento.co2backend.domain

data class Room(
    var name: String? = null,
    var condition: Condition? = Condition(),
    val sensors: MutableList<Sensor> = mutableListOf()
)

data class RoomDtoIn(
    val name: String,
    val sensor: String
)
