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
    
    /* 
    USE `ecommerce`;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `users`
--

INSERT INTO `users` 
VALUES 
('yassine','{bcrypt}$2a$10$Na7.uPg0ceL/Ut6tfzl3VusGYtCoJ89oBH63wMoFu1B0TmzWOtjoa',1),
('sara','{bcrypt}$2a$10$RhAgTjOFJJC8aLLLGZ1AmO3RK3J5loX/y/rAbvFFdqhXRI36xr69O',1),
('youness','{bcrypt}$2a$10$wXzdZtu6fEN7VOa2PHpHq.dwK7y0kOaQGUpk74GPBH43uZMT5wxrm',1);


--
-- Table structure for table `authorities`
--

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('yassine','ROLE_GUEST'),
('sara','ROLE_VENDOR'),
('youness','ROLE_ADMIN')
    */
    
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
                .requestMatchers(HttpMethod.PUT, "/api/profiles/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/profiles/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/details/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/orders/**").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/details/**").hasAnyRole("ADMIN")
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
