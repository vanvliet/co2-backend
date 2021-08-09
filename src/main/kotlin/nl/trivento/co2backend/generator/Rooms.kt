package nl.trivento.co2backend.generator

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Room

object Rooms {

    val rooms = mutableSetOf(
        Room(
            name = "Kamer-1-23",
            condition = Condition(),
            sensors = arrayListOf("00:11:22:33:44:55")
        )
    )
}
