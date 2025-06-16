package com.project.speedroller.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                "/",
                "/corporativo/mision",
                "/corporativo/vision", 
                "/corporativo/valores",
                "/eventos",
                "/horarios/estudiantes",
                "/horarios/instructores"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll())
            .build();
    }
}