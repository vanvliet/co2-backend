package nl.trivento.co2backend.data

import nl.trivento.co2backend.domain.Condition
import nl.trivento.co2backend.domain.Room

object Rooms {

    val rooms = mutableSetOf(
        Room(
            name = "Kamer-1.23",
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:23")
        ),
        Room(
            name = "Kamer-1.24",
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:24")
        ),
        Room(
            name = "Kamer-1.25",
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:25")
        ),
        Room(
            name = "Kamer-2.01",
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:01")
        ),
        Room(
            name = "Kamer-2.02",
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:02")
        ),
        Room(
            name = null,
            condition = Condition(co2 = 345.0),
            sensors = arrayListOf("00:11:22:33:44:03")
        ),
    )
}
