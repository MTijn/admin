package nl.martijnklene.admin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange { exchanges ->
            exchanges.pathMatchers("/v1/**", "/admin.css", "/favicon.ico").permitAll()
            .anyExchange().authenticated()
        }.oauth2Login()
        return http.build()
    }
}
