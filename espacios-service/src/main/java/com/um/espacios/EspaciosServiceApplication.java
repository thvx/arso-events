package com.um.espacios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.um.espacios")
@EnableMongoRepositories(basePackages = "com.um.espacios.infrastructure.adapters.output.persistence")
@EnableConfigurationProperties
public class EspaciosServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EspaciosServiceApplication.class, args);
    }
}
