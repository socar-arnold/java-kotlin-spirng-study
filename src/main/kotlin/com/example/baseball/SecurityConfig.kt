package com.example.baseball

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.http.HttpMethod

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtAuthFilter: JwtAuthFilter,) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http.csrf { it.disable() }.sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 안쓰고 JWT
    }.authorizeHttpRequests {
        it
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers("/auth/**", "/hello/**", "/visit", "/stats").permitAll().anyRequest().authenticated()
    }.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java).build()
}