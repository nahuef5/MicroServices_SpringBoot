package com.impl.gatewayApi.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class MainSecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilter(ServerHttpSecurity http){
        http.authorizeExchange().anyExchange().authenticated()
                .and()
                .oauth2Client()
                .and()
                .oauth2ResourceServer().jwt();
                
        return http.build();
    }
}
