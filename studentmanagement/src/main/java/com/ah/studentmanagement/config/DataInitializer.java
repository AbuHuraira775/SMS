package com.ah.studentmanagement.config;

import com.ah.studentmanagement.entity.Users;
import com.ah.studentmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author "Abu Huraira"
 **/

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSampleData(UserRepository userRepository, PasswordEncoder passwordEncoder){

        return args -> {
            if(!userRepository.existsByUsername("Admin")){
            Users user = new Users();
            user.setUsername("Admin");
            user.setPassword(passwordEncoder.encode("Admin@123"));
            user.setActive(true);
            userRepository.save(user);
            }
        };
    }
}
