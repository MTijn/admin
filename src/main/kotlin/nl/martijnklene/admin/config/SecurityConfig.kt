import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    public override fun configure(http: HttpSecurity) {
        http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/login**", "/error**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .deleteCookies()
            .invalidateHttpSession(true)
    }
}
