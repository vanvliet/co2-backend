package nl.trivento.co2backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling

class Co2backendApplication

fun main(args: Array<String>) {
	runApplication<Co2backendApplication>(*args)
}
