package nl.martijnklene.admin.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.io.ClassPathResource
import org.springframework.web.filter.ForwardedHeaderFilter
import org.springframework.web.servlet.function.RouterFunctions.resources

@Configuration
class WebConfig {
    @Bean
    fun resRouter() = resources("/**", ClassPathResource("static/"))

    @Bean
    fun forwardedHeaderFilter(): FilterRegistrationBean<ForwardedHeaderFilter>? {
        val filterRegistrationBean = FilterRegistrationBean<ForwardedHeaderFilter>()
        filterRegistrationBean.filter = ForwardedHeaderFilter()
        filterRegistrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return filterRegistrationBean
    }
}
