package pl.msui.mongoconnector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoConnectorApplication

fun main(args: Array<String>) {
	runApplication<MongoConnectorApplication>(*args)
}
