package nl.trivento.co2backend.data

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Room

object Rooms {

    val rooms = mutableSetOf(
        Room(
            name = "Auditorium",
            condition = Condition(),
            sensors = arrayListOf("urn:dev:DEVEUI:A81758FFFE05A01F")
        )
    )
}
