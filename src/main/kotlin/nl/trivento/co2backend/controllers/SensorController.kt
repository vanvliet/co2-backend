package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.data.Rooms
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/sensors")
@CrossOrigin("*")
class SensorController {

    @GetMapping("")
    fun getAllSensor(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(Rooms.rooms.filter { it.name == null }.flatMap { it.sensors })
    }
}
