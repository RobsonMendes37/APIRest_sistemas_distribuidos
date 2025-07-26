package br.ufc.quixada.hospital_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

//@Configuration e @EnableWebSecurity diz ao Spring que esta classe contém configurações de segurança.

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //Desabilita a proteção CSRF, que não é necessária para nossa API stateless.
                .csrf(csrf -> csrf.disable())

                // Define que TODAS as requisições HTTP precisam ser autenticadas.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )

                // Habilita a Autenticação Básica (HTTP Basic Authentication).
                .httpBasic(withDefaults());

        return http.build();
    }
}