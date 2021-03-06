package nl.trivento.co2backend.senml

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Message
import nl.trivento.co2backend.data.Rooms
import nl.trivento.co2backend.domain.Sensor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SenML {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    fun toMessage(pack: String): Message? {
        val normalizedSenMLs = pack.toRawSenMLs().normalize()
        if (normalizedSenMLs.isEmpty()) {
            logger.warn("Received pack does not contain valid SenML")
            return null
        }

        val measurementCollection = normalizedSenMLs.toMeasurementCollection()
        val timeStamp = if (normalizedSenMLs.isNotEmpty()) normalizedSenMLs[0].t else Instant.now()
        val condition = Condition(
            co2 = measurementCollection.getDoubleMeasurement("CO2Concentration")?.value,
            temperature = measurementCollection.getDoubleMeasurement("temperature")?.value,
            humidity = measurementCollection.getDoubleMeasurement("humidity")?.value,
            lastUpdate = timeStamp
        )

        val sensorName = normalizedSenMLs[0].n.substringBeforeLast(":")
        val room = Rooms.rooms.find { it.sensor == Sensor(sensorName) }
        if (room == null) {
            logger.info("Message received from Sensor $sensorName and ignored.")
            return null
        }
        room.condition = condition

        return Message(
            name = room.name,
            condition = condition,
        )
    }
}
