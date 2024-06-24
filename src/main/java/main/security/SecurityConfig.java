/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author hp
 */
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource datasource){
        return new JdbcUserDetailsManager(datasource);
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers(HttpMethod.GET, "/api/products").hasAnyRole("GUEST", "VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/categories").hasAnyRole("GUEST", "VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/categories/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/categories/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/categories/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/profiles").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/profiles/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/profiles/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/details/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/details").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("VENDOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("VENDOR", "ADMIN")
        );
        
        http.httpBasic(Customizer.withDefaults());
        http.csrf(x -> x.disable());
        return http.build();
    }
}
