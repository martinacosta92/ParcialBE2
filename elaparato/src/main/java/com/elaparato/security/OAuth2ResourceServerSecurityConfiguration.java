package com.elaparato.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authorize) -> authorize
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer((oauth2) -> oauth2
                    .jwt((jwt) -> jwt
                            .jwtAuthenticationConverter(new KeyCloakJwtAuthenticationConverter())
                    )
            );

    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/el-aparato-Acosta/protocol/openid-connect/certs").build();
  }
}

