package nl.trivento.co2backend.domain

data class Room(
    var name: String,
    var condition: Condition? = Condition(),
    var sensor: Sensor
)

data class RoomDtoIn(
    val name: String,
    val sensor: String
) {
    fun toRoom(): Room =  Room(
        name = this.name,
        condition = Condition(),
        sensor = Sensor(this.sensor)
    )
}
