package nl.trivento.co2backend.senml

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Message
import nl.trivento.co2backend.generator.Rooms
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SenML {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    fun toMessage(pack: String): Message? {
        val normalizedSenMLs = pack.toRawSenMLs().normalize()
        if (normalizedSenMLs.size == 0) {
            logger.info("Received pack does not contain valid SenML")
            return null
        }

        val measurementCollection = normalizedSenMLs.toMeasurementCollection()

        val room = if (normalizedSenMLs.isNotEmpty())
            Rooms.rooms
                .find { it.sensors.contains(normalizedSenMLs[0].n.substringBeforeLast(":")) }
        else null
        val timeStamp = if (normalizedSenMLs.isNotEmpty()) normalizedSenMLs[0].t else Instant.now()

        val condition = Condition(
            co2 = measurementCollection.getDoubleMeasurement("CO2Concentration")?.value,
        )
        if (room != null ) room.condition = condition

        return Message(
            timeStamp = timeStamp.toEpochMilli(),
            name = room?.name ?: "Sensor: ${normalizedSenMLs[0].n.substringBeforeLast(':')}",
            condition = condition)
    }
}
