package nl.martijnklene.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.servlet.function.RouterFunctions.resources

@Configuration
class WebConfig {
    @Bean
    fun resRouter() = resources("/**", ClassPathResource("static/"))
}
