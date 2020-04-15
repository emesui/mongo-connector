package pl.msui.mongoconnector.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class ApplicationConfig {

    @Bean
    fun mappingJackson2HttpMessageConverter() = MappingJackson2HttpMessageConverter()

    @Bean
    fun kotlinModule() = KotlinModule()

}