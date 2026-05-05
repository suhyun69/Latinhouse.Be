package com.latinhouse.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**"
                        ).permitAll()
                        // Lesson: GET 조회 공개, PUT/PATCH/DELETE 인증 필요
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/v1/lessons",
                                "/api/v1/lessons/**"
                        ).permitAll()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/v1/lessons/**"
                        ).authenticated()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PATCH,
                                "/api/v1/lessons/**"
                        ).authenticated()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/v1/lessons/**"
                        ).authenticated()
                        // Profile: GET 조회 공개, POST/PUT/PATCH/DELETE 인증 필요
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET,
                                "/api/v1/profiles",
                                "/api/v1/profiles/**"
                        ).permitAll()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,
                                "/api/v1/profiles"
                        ).authenticated()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,
                                "/api/v1/profiles/**"
                        ).authenticated()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PATCH,
                                "/api/v1/profiles/**"
                        ).authenticated()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE,
                                "/api/v1/profiles/**"
                        ).authenticated()
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
