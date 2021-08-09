package nl.trivento.co2backend.websockets

import nl.trivento.co2backend.senml.SenML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class WebSocketMessages(
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {

    @Autowired
    lateinit var senML: SenML

    fun sendSenmlMessage(pack: String) {
        simpMessagingTemplate.convertAndSend(
            "/topic/room-message",
            senML.toMessage(pack)
        )
    }
}