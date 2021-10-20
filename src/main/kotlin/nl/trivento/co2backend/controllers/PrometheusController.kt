package nl.trivento.co2backend.controllers

import nl.trivento.co2backend.data.Rooms
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/metrics")
@CrossOrigin("*")
class PrometheusController {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("", produces = arrayOf(MediaType.TEXT_PLAIN_VALUE))
    fun generateMetrics(): ResponseEntity<Any> {
        val metrics = "# HELP c02 CO2 value, in ppm\n" +
                "# TYPE co2 gauge\n" +
                Rooms.rooms.mapNotNull {
                    if (it.condition?.co2 == null)
                        null
                    else {
                        val idString = "{id=\"AirGradient\",sensor=\"${it.name}\"}";
                        "co2${idString} ${it.condition?.co2 ?: 0}"
                    }
                }.joinToString(separator = "\n", postfix = "\n")

        return ResponseEntity.ok().body(metrics)
    }
}
