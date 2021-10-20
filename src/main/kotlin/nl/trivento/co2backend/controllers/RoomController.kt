package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.data.Rooms
import nl.trivento.co2backend.domain.RoomDtoIn
import nl.trivento.co2backend.domain.Sensor
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

    @GetMapping("/{roomName}")
    fun getRoom(@PathVariable(value = "roomName") name: String): ResponseEntity<Any> {
        val room = Rooms.rooms.find { it.name == name }
        return if (room == null)
            ResponseEntity.notFound().build()
        else
            ResponseEntity.ok().body(room)
    }

    @DeleteMapping("/{roomName}")
    fun removeRoom(@PathVariable(value = "roomName") name: String): ResponseEntity<Any> {
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
        val roomWithNewRoomName = Rooms.rooms.find { it.name == newRoom.name }
        val roomsWithNewRoomSensor = Rooms.rooms.filter { it.sensor == Sensor(newRoom.sensor) }
        return if (roomWithNewRoomName != null || roomsWithNewRoomSensor.isNotEmpty()) {
            ResponseEntity.status(400).body("Sensor ${newRoom.sensor} or room ${newRoom.name} already exists.")
        } else {
            Rooms.rooms.add(newRoom.toRoom())
            ResponseEntity.ok().body(newRoom)
        }
    }

    @PatchMapping("/{roomName}/name")
    fun updateRoomName(
        @PathVariable(value = "roomName") oldRoomName: String,
        @Valid @RequestBody updateRoomName: UpdateRoomName
    ): ResponseEntity<Any> {
        val roomWithOldRoomName = Rooms.rooms.find { it.name == oldRoomName }
        val roomWithNewRoomName = Rooms.rooms.find { it.name == updateRoomName.newRoomName }
        return if (roomWithNewRoomName != null) {
            ResponseEntity.status(400).body("Room $updateRoomName already exists.")
        } else if (roomWithOldRoomName == null) {
            ResponseEntity.status(404).body("Room $oldRoomName not found.")
        } else {
            roomWithOldRoomName.name = updateRoomName.newRoomName
            ResponseEntity.ok().body(roomWithOldRoomName)
        }
    }

    @PatchMapping("/{roomName}/sensor")
    fun updateRoomSensor(
        @PathVariable(value = "roomName") roomName: String,
        @Valid @RequestBody updateRoomSensor: UpdateRoomSensor
    ): ResponseEntity<Any> {
        val roomWithName = Rooms.rooms.find { it.name == roomName }
        val roomWithSensor = Rooms.rooms.find { it.sensor == Sensor(updateRoomSensor.newSensorName) }
        return if (roomWithName == null) {
            ResponseEntity.status(404).body("Room $roomName not found.")
        } else if (roomWithSensor != null) {
            ResponseEntity.status(400)
                .body("Sensor ${updateRoomSensor.newSensorName} already exists in room ${roomWithSensor.name}.")
        } else {
            roomWithName.sensor = Sensor(updateRoomSensor.newSensorName)
            ResponseEntity.ok().body(roomWithName)
        }
    }

    @PatchMapping("/{roomName}")
    fun moveSensor(
        @PathVariable(value = "roomName") fromRoomName: String,
        @Valid @RequestBody moveSensor: MoveSensor
    ): ResponseEntity<Any> {
        val roomFromName = Rooms.rooms.find { it.name == fromRoomName }
        val roomToName = Rooms.rooms.find { it.name == moveSensor.toRoomName }
        return if (roomFromName == null) {
            ResponseEntity.status(404).body("Room $fromRoomName not found.")
        } else if (roomToName == null) {
            ResponseEntity.status(404).body("Room ${moveSensor.toRoomName} not found.")
        } else {
            roomToName.sensor = roomFromName.sensor
            Rooms.rooms.remove(roomFromName)
            ResponseEntity.ok().body(roomToName)
        }
    }

    data class UpdateRoomName(val newRoomName: String)
    data class UpdateRoomSensor(val newSensorName: String)
    data class MoveSensor(val toRoomName: String)
}

