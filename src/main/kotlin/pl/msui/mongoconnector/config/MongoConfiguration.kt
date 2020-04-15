package pl.msui.mongoconnector.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ReadPreference
import com.mongodb.WriteConcern
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import pl.msui.mongoconnector.meter.MongoMetricCommandListener

@Profile("!integration")
@Configuration
class MongoConfiguration(
    private val commandListener: MongoMetricCommandListener
): AbstractMongoClientConfiguration() {

    @Bean
    override fun mongoClient(): MongoClient = MongoClients.create(mongoClientSettings())

    override fun getDatabaseName(): String = connectionString.database!!

    private fun mongoClientSettings(): MongoClientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .writeConcern(WriteConcern.MAJORITY)
            .readPreference(ReadPreference.nearest())
            .addCommandListener(commandListener)
            .applyToConnectionPoolSettings { }
            .applyToSocketSettings { }
            .applyToServerSettings { }
            .applyToClusterSettings {
//                it.applyConnectionString(connectionString)
//                    .localThreshold(localThresholdMs, MILLISECONDS)
            }
            .build()

    companion object {
        val connectionString = ConnectionString(
            "mongodb://mongo1:27017,mongo1:27018,mongo1:27019/test?replicaSet=rs0")
        const val localThresholdMs: Long = 8
    }
}
