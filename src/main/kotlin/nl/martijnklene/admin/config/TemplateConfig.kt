package nl.martijnklene.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.ViewResolverRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer

@Configuration
@EnableWebFlux
class TemplateConfig : WebFluxConfigurer {
    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.freeMarker()
    }

    @Bean
    fun freeMarkerConfigurer() = FreeMarkerConfigurer().apply {
        setTemplateLoaderPath("classpath:/templates")
    }
}
