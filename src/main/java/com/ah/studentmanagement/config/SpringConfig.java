package com.ah.studentmanagement.config;

import com.ah.studentmanagement.entity.Users;
import com.ah.studentmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author "Abu Huraira"
 **/

@Configuration
@EnableWebSecurity
public class SpringConfig {

    private static final String[] PUBLIC_PATH = {
            "/login",
            "/error",
            "/css/**",
            "/images/**",
            "/js/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(PUBLIC_PATH).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout( logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return  http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
