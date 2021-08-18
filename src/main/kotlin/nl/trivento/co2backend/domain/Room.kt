package nl.trivento.co2backend.domain

data class Room(
    var name: String? = null,
    var condition: Condition? = Condition(),
    val sensors: MutableList<String> = mutableListOf()
) {
    fun toSensor(): Sensor = Sensor(this.sensors[0])
}

data class RoomDtoIn(
    val name: String,
    val sensor: String
)
