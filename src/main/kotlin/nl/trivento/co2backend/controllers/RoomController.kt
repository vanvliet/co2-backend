package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.domain.Room
import nl.trivento.co2backend.data.Rooms
import nl.trivento.co2backend.domain.RoomDtoIn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/rooms")
@CrossOrigin("*")
class RoomController {

    @GetMapping("")
    fun getAllRoom(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(Rooms.rooms.filter { it.name != null })
    }

    @GetMapping("/{name}")
    fun getRoom(@PathVariable(value = "name") name: String): ResponseEntity<Any> {
        val room = Rooms.rooms.find { it.name == name }
        return if (room == null)
            ResponseEntity.notFound().build()
        else
            ResponseEntity.ok().body(room)
    }

    @DeleteMapping("/{name}")
    fun removeRoom(@PathVariable(value = "name") name: String): ResponseEntity<Any> {
        val room = Rooms.rooms.find { it.name == name }
        return if (room == null)
            ResponseEntity.notFound().build()
        else {
            Rooms.rooms.remove(room)
            ResponseEntity.ok().body("Room $name removed")
        }
    }

    @PostMapping("")
    fun createNewRoom(@Valid @RequestBody newRoom: RoomDtoIn): ResponseEntity<Any> {
        val rooms = Rooms.rooms.filter { it.sensors.contains(newRoom.sensor) }
        if (rooms.size > 1) {
            return ResponseEntity
                .status(500)
                .body("Sensor ${newRoom.sensor} is in multple rooms: ${rooms.map { it.name }.joinToString { ", " }}.")
        } else if (rooms.size == 0) {
            return ResponseEntity.badRequest().body("Sensor ${newRoom.sensor} is unknown.")
        } else if (rooms[0].name == null) {
            val roomOnName = Rooms.rooms.find { it.name == newRoom.name }
            if (roomOnName == null) {
                rooms[0].name = newRoom.name
                return ResponseEntity.ok().body(rooms[0])
            } else {
                roomOnName.sensors.add(newRoom.sensor)
                Rooms.rooms.remove(rooms[0])
                return ResponseEntity.ok().body(roomOnName)
            }
        } else if (rooms[0].name == newRoom.name) {
            return ResponseEntity.ok("Room ${newRoom.name} already existed.")
        } else {
            return ResponseEntity.badRequest().body("Sensor ${newRoom.sensor} is in room ${rooms[0].name}.")
        }
    }
}
