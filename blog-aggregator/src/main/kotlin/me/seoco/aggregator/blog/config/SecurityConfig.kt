package me.seoco.aggregator.blog.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager = false)
class SecurityConfig {

    @Value("\${auth.admin.id}")
    lateinit var adminId: String

    @Value("\${auth.admin.password}")
    lateinit var adminPassword: String

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            csrf { disable() }
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
            httpBasic { }
        }
    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val userDetails = User.withDefaultPasswordEncoder()
            .username(adminId)
            .password(adminPassword)
            .roles("ADMIN")
            .build()
        return MapReactiveUserDetailsService(userDetails)
    }
}
