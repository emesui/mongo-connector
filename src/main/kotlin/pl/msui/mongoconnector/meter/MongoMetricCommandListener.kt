package pl.msui.mongoconnector.meter

import com.mongodb.event.CommandEvent
import com.mongodb.event.CommandFailedEvent
import com.mongodb.event.CommandListener
import com.mongodb.event.CommandStartedEvent
import com.mongodb.event.CommandSucceededEvent
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Tags
import io.micrometer.core.instrument.Timer
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MongoMetricCommandListener(
    private val metricRegistry: MeterRegistry
) : CommandListener {

    private val timers: MutableMap<Tags, Timer> = mutableMapOf()

    override fun commandStarted(event: CommandStartedEvent) {}

    override fun commandSucceeded(event: CommandSucceededEvent) {
        updateMongoCommandTimer(
            event.commandName,
            getHostNormalized(event),
            event.getElapsedTime(TimeUnit.NANOSECONDS),
            true
        )
    }

    override fun commandFailed(event: CommandFailedEvent) {
        updateMongoCommandTimer(
            event.commandName,
            getHostNormalized(event),
            event.getElapsedTime(TimeUnit.NANOSECONDS),
            false
        )
    }

    private fun updateMongoCommandTimer(commandName: String, host: String, elapsedNanos: Long, success: Boolean) {
        val tags: Tags = Tags.of(
            Tag.of("command", commandName),
            Tag.of("host", host),
            Tag.of("success", success.toString())
        )
        val timer: Timer = timers.computeIfAbsent(tags) { metricRegistry.timer("commandTimer", it) }
        timer.record(elapsedNanos, TimeUnit.NANOSECONDS)
    }

    private fun getHostNormalized(event: CommandEvent): String {
        return event.connectionDescription.serverAddress.host
    }
}