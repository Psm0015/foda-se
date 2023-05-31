package com.fodase.fodase.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
        .csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/**").permitAll()
        .requestMatchers("/convidado/**").permitAll()
        .requestMatchers("/adminpg**").permitAll()
        .requestMatchers("/cad**").permitAll()
        .requestMatchers("/index**").permitAll()
        .requestMatchers("/login-1**").permitAll()
        .requestMatchers("/static/**").permitAll()
        .requestMatchers("/js/**").permitAll()
        .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        
    }

}
