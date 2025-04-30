package nl.martijnklene.admin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/v1/**", permitAll)
                authorize("/sitemap.xml", permitAll)
                authorize("/**", authenticated)
            }
            oauth2Login {}
            logout {
                invalidateHttpSession = true
                clearAuthentication = true
                deleteCookies("JSESSIONID")
                logoutRequestMatcher = AntPathRequestMatcher("/logout")
            }
        }
        return http.build()
    }
}
