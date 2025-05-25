package com.um.reservas.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.um.reservas.infrastructure.output.persistence.repository")
@EnableMongoAuditing
public class MongoConfig {
}