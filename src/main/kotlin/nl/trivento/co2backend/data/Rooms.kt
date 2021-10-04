package nl.trivento.co2backend.data

import nl.trivento.co2backend.domain.Room
import nl.trivento.co2backend.domain.Sensor

object Rooms {

    val rooms = mutableSetOf(
        Room(name = "Auditorium", sensor = Sensor("urn:dev:DEVEUI:A81758FFFE05A01F")),
        Room( name =  "Kubus 4", sensor = Sensor( "kubus4")),
        Room( name =  "Zaal 4.01", sensor = Sensor( "zaal41")),
        Room( name =  "Zaal 4.02", sensor = Sensor( "zaal42")),
        Room( name =  "Kubus 5", sensor = Sensor( "kubus5")),
        Room( name =  "Zaal 5.01", sensor = Sensor( "zaal51")),
        Room( name =  "Zaal 5.02", sensor = Sensor( "29b28c"))
    )}

