package com.um.espacios.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "bus";
    public static final String QUEUE_ESPACIOS = "espacios";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue espaciosQueue() {
        return new Queue(QUEUE_ESPACIOS);
    }

    @Bean
    public Binding espaciosBinding(Queue espaciosQueue, TopicExchange exchange) {
        return BindingBuilder.bind(espaciosQueue)
                .to(exchange)
                .with("bus.espacios.#");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}