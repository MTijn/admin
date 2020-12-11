package nl.martijnklene.admin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication
@PropertySources(
    PropertySource("classpath:application.yaml"),
    PropertySource("file:/run/secrets/blog_config", ignoreResourceNotFound = true)
)
class AdminApplication

fun main(args: Array<String>) {
    runApplication<AdminApplication>(*args)
}
