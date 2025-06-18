package com.exemple.facturation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  // Active la sécurité Web dans l'application
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF, utile pour API REST simples (pas de formulaires)
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/h2-console/**").permitAll()    // Autorise l'accès libre à la console H2 (base en mémoire)
                    .requestMatchers("/clients/**").permitAll()      // Autorise accès public aux routes /clients
                    .requestMatchers("/factures/**").permitAll()     // Autorise accès public aux routes /factures
                    .anyRequest().authenticated()                     // Toutes les autres requêtes nécessitent une authentification
            )
            .httpBasic(Customizer.withDefaults()); // Active l'authentification HTTP Basic (login+mot de passe dans l'entête HTTP)
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Création d'un utilisateur en mémoire avec username "user" et password "password"
        UserDetails user = User.withDefaultPasswordEncoder()  // Encode le mot de passe en clair (pas sécurisé en prod)
            .username("user")
            .password("password")
            .roles("USER")     // Rôle attribué à cet utilisateur
            .build();
        // Stocke l'utilisateur dans un gestionnaire en mémoire (pas de base de données)
        return new InMemoryUserDetailsManager(user);
    }
}
