package nl.martijnklene.admin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig {
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer? =
        WebSecurityCustomizer { web: WebSecurity ->
            web
                .ignoring()
                .requestMatchers("/v1/**")
                .requestMatchers("/sitemap.xml")
        }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/v1/**").permitAll()
                    .requestMatchers("/sitemap.xml").permitAll()
                    .requestMatchers("/**").authenticated()
            }
            .oauth2Login {}
            .logout { logout ->
                logout
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            }
        return http.build()
    }
}
