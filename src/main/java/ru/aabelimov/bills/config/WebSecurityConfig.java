package ru.aabelimov.bills.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.aabelimov.bills.entity.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/users/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/orders/**", "/order-stages/**", "/bills/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/orders/**", "/order-stages/**", "/bills/**").hasAnyAuthority(Role.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/orders/**", "/order-stages/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/bills/**").permitAll()
                        .requestMatchers("/style/**").permitAll()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
