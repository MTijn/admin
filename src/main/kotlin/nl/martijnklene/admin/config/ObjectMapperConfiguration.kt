package nl.martijnklene.admin.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperConfiguration {
    @Bean
    @Primary
    fun objectMapperConfigurationAppender(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(ZonedDateTime::class.java, ZonedDateTimeSerializer(dateTimeFormatter))

        val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
        javaTimeModule.addSerializer(LocalDate::class.java, LocalDateSerializer(dateFormatter))
        javaTimeModule.addDeserializer(LocalDate::class.java, LocalDateDeserializer(dateFormatter))

        mapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        mapper.registerModule(javaTimeModule)
        mapper.registerModule(KotlinModule.Builder().build())

        return mapper
    }

    @Bean
    fun xmlMapper(): XmlMapper {
        val mapper = XmlMapper(JacksonXmlModule().apply { setDefaultUseWrapper(false) })

        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true)
        mapper.registerModule(KotlinModule.Builder().build())
        return mapper
    }
}
