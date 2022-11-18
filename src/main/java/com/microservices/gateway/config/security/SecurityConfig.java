package com.microservices.gateway.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final List<String> ALLOWED_ORIGINS = List.of("http://localhost:8763");

    private static final String ALLOWED_METHODS = "*";
    private static final String ALLOWED_HEADERS = "*";
    private static final String EXPOSED_HEADERS = "Content-Disposition";
    private static final String PATH = "/**";

    @Bean
    public Mono<Jwt> jtw() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> context.getAuthentication().getPrincipal())
                .cast(Jwt.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList(ALLOWED_METHODS));
        configuration.setAllowedHeaders(Collections.singletonList(ALLOWED_HEADERS));
        configuration.setExposedHeaders(Collections.singletonList(EXPOSED_HEADERS));
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATH, configuration);
        return source;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling(this::handleExceptions)
                .authorizeExchange()
                .pathMatchers(SecuredPaths.ALL_METHODS).authenticated()
                .anyExchange().permitAll()
                .and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer()
                .jwt();
        return http.build();
    }

    private void handleExceptions(ServerHttpSecurity.ExceptionHandlingSpec handlingSpec) {
        handlingSpec.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED));
    }
}
