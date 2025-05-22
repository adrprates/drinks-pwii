package com.pwii.drinks.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/home", "/register", "/saveUser").permitAll()
                        .requestMatchers("/drink/*").hasAuthority("Admin")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .defaultSuccessUrl("/", true));

        return http.build();

    }
}
