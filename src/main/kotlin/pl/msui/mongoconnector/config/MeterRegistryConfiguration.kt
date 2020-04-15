package pl.msui.mongoconnector.config;

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MeterRegistryConfiguration {

    @Bean
    fun meterRegistry(): MeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
}
