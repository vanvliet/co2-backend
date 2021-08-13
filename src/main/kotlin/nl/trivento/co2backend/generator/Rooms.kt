package nl.trivento.co2backend.generator

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Room

object Rooms {

    val rooms = mutableSetOf(
        Room(
            name = "Kamer-1.23",
            sensors = arrayListOf("00:11:22:33:44:23")
        ),
        Room(
            name = "Kamer-1.24",
            sensors = arrayListOf("00:11:22:33:44:24")
        ),
        Room(
            name = "Kamer-1.25",
            sensors = arrayListOf("00:11:22:33:44:25")
        ),
        Room(
            name = "Kamer-2.01",
            sensors = arrayListOf("00:11:22:33:44:01")
        ),
        Room(
            name = "Kamer-2.02",
            sensors = arrayListOf("00:11:22:33:44:02")
        ),
        Room(
            name = "Kamer-2.03",
            sensors = arrayListOf("00:11:22:33:44:03")
        ),
    )
}
