package com.tistory.shanepark.dutypark.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.requiresChannel {
            it.anyRequest().requiresSecure()
        }.authorizeHttpRequests()
            .anyRequest()
            .permitAll()
            .and().build()
    }
}
