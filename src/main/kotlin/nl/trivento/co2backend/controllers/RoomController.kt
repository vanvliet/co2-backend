package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.domain.Room
import nl.trivento.co2backend.generator.Rooms
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/rooms")
@CrossOrigin("*")
class RoomController {

    @GetMapping("")
    fun getAllRoom(): ResponseEntity<Any> {
        return ResponseEntity.ok().body(Rooms.rooms)
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
    fun createNewRoom(@Valid @RequestBody room: Room): ResponseEntity<Any> {
        if ( Rooms.rooms.find {it.name == room.name  } == null)
            return ResponseEntity.ok().body(Rooms.rooms.add(room))
        else
            return ResponseEntity.badRequest().body("Room ${room.name} already exists.")
    }
}
