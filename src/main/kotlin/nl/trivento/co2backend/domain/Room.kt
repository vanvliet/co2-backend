package nl.trivento.co2backend.domain

data class Room(
    val name: String? = null,
    var condition: Condition? = Condition(),
    val sensors: List<String> = emptyList()
) {
    fun toSensor(): Sensor = Sensor(this.sensors[0])
}
