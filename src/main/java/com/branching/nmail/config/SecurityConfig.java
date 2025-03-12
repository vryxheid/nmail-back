package com.branching.nmail.config;

import com.branching.nmail.config.filter.JWTFilter;
import com.branching.nmail.model.User;
import com.branching.nmail.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Tell Spring that this is a configuration class where beans will be defined
@EnableWebSecurity // Tell Spring Security to use this config instead of using the default filter flow
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    // Bean to get customized filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable()) // disable csrf. We will make http stateless (ie, regenerate session id in every request)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/register", "/auth/login") // Match "register" and "login" urls
                        .permitAll() // Permit requests matched in the prev line
                        .anyRequest().authenticated()) // require authentication on every request. By default, it checks username and password. We want Jwt, so we specify it later using addFilterBefore
//                .formLogin(Customizer.withDefaults()) // Enable default login form. We will use our own one
//                .httpBasic(Customizer.withDefaults()) // For postman requests, for example, where I cannot see login page
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /* LEARNING PURPOSES, hardcoded users */
//    @Bean
//    public UserDetailsService userDetailsService () {
//
//
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("user1").password("user1").roles("USER").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder()
//                .username("user2").password("user2").roles("ADMIN").build();
//
//        // InMemoryUserDetailsManager class implements UserDetailsService
//        // One of its constructors gets a list of users (List<UserDetails>)
//        return new InMemoryUserDetailsManager(user1, user2);
//
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // DaoAuthenticationProvider class Implements DaoAuthenticationProvider interface
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(customUserDetailsService); // Call my CustomUserDetailsService
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // The class implements the interface AuthenticationManager
        return config.getAuthenticationManager();
    }
}
