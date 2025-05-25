package com.um.reservas.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.reserva-creada.name}")
    private String reservaCreadaQueue;

    @Value("${rabbitmq.queue.reserva-cancelada.name}")
    private String reservaCanceladaQueue;

    @Value("${rabbitmq.routing.reserva-creada.key}")
    private String reservaCreadaRoutingKey;

    @Value("${rabbitmq.routing.reserva-cancelada.key}")
    private String reservaCanceladaRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue reservaCreadaQueue() {
        return new Queue(reservaCreadaQueue);
    }

    @Bean
    public Queue reservaCanceladaQueue() {
        return new Queue(reservaCanceladaQueue);
    }

    @Bean
    public Binding reservaCreadaBinding() {
        return BindingBuilder.bind(reservaCreadaQueue())
                .to(exchange())
                .with(reservaCreadaRoutingKey);
    }

    @Bean
    public Binding reservaCanceladaBinding() {
        return BindingBuilder.bind(reservaCanceladaQueue())
                .to(exchange())
                .with(reservaCanceladaRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}