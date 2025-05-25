package com.um.pasarela.config;

import com.um.pasarela.filter.JwtPreFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZuulConfig {

    @Bean
    public JwtPreFilter jwtPreFilter() {
        return new JwtPreFilter();
    }
}