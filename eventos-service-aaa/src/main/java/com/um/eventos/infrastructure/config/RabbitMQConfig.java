package com.um.eventos.infrastructure.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import com.rabbitmq.client.ConnectionFactory;

@ApplicationScoped
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "bus-eventos";

    @Produces
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getProperty("rabbitmq.host", "rabbitmq"));
        factory.setPort(Integer.parseInt(System.getProperty("rabbitmq.port", "5672")));
        factory.setUsername(System.getProperty("rabbitmq.username", "guest"));
        factory.setPassword(System.getProperty("rabbitmq.password", "guest"));
        return factory;
    }

    @Produces
    public String exchangeName() {
        return EXCHANGE_NAME;
    }
}