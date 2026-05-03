package com.ah.studentmanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author "Abu Huraira"
 **/

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
