package nl.trivento.co2backend.data

import nl.trivento.co2backend.domain.Room
import nl.trivento.co2backend.domain.Sensor

object Rooms {

    val rooms = mutableSetOf(
        Room(name = "Auditorium", sensor = Sensor("urn:dev:DEVEUI:A81758FFFE05A01F")),
        Room( name =  "Kubus 4", sensor = Sensor( "kubus4")),
        Room( name =  "Kamer 4.01", sensor = Sensor( "kamer41")),
        Room( name =  "Kamer 4.02", sensor = Sensor( "kamer42")),
        Room( name =  "Kubus 5", sensor = Sensor( "kubus5")),
        Room( name =  "Kamer 5.01", sensor = Sensor( "kamer51")),
        Room( name =  "Kamer 5.02", sensor = Sensor( "kamer52"))
    )}

