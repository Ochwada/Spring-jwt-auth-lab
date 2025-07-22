package com.ochwada.jwt_auth_lab.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * *******************************************************
 * Package: com.ochwada.jwt_auth_lab.config
 * File: SecurityConfig.java
 * Author: Ochwada
 * Date: Monday, 21.Jul.2025, 6:04 PM
 * Description:
 * Objective:
 * *******************************************************
 */

/**
 * -------------------------------------
 * 🔐 Spring Security Configuration
 * -------------------------------------
 * This class configures Spring Security for the application.
 * It:
 *  - Defines an in-memory user for testing login
 *  - Sets up basic authentication rules
 *  - Enables a custom login page at /login
 */
@Configuration
public class SecurityConfig {

    /**
     * -------------------------------------
     * 1. In-Memory UserDetailsService
     * -------------------------------------
     * Defines a single test user
     * The password is stored in plain text using `{noop}` prefix.
     *
     * @return an in-memory user details manager with one test user
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("john")
                .password("{noop}password")  // 🟡 use plain text for testing only
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * -------------------------------------
     * 2. Security Filter Chain
     * -------------------------------------
     * Configures HTTP security rules:
     *  - All requests require authentication
     *  - Custom login page at /login is permitted to everyone
     *
     * @param http the HttpSecurity object to customize
     * @return the configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")   // custom login page
                        .permitAll()
                )
                .build();
    }
}
