package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.websockets.WebSocketMessages
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/senml")
@CrossOrigin("*")
class SenmlController {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @Autowired
    lateinit var scheduledWebSocketMessages: WebSocketMessages

    @PostMapping("")
    fun sendMessage(@RequestBody pack: String): ResponseEntity<Any> {
        logger.info(pack)
        scheduledWebSocketMessages.sendSenmlMessage(pack)
        return ResponseEntity.accepted().body("SenML pack accepted")
    }
}
