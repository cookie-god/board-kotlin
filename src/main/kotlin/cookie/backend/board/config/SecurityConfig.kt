package cookie.backend.board.config

import cookie.backend.board.config.filter.JwtAuthenticationFilter
import cookie.backend.board.config.jwt.JwtTokenProvider
import cookie.backend.board.domain.auth.AuthService
import kisung.template.board.config.filter.LoggingFilter
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfig(val jwtTokenProvider: JwtTokenProvider, val authService: AuthService) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/api/v1/auth/**").permitAll()
                it.requestMatchers(
                    "/swagger",
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/api-docs",
                    "/api-docs/**",
                    "/v3/api-docs/**"
                ).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(
                LoggingFilter(), UsernamePasswordAuthenticationFilter::class.java

            )
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, authService),
                LoggingFilter::class.java
            )
        return http.build()
    }
}