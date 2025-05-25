package com.um.eventos.infrastructure.adapters.output.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.um.eventos.application.ports.output.EventPublisherPort;
import com.um.eventos.domain.events.DomainEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class RabbitEventPublisher implements EventPublisherPort {

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    String exchangeName;

    @Override
    public void publishEvent(DomainEvent event) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, "topic", true);

            String routingKey = "bus.eventos." + event.getEventType();
            String message = event.toJson();

            channel.basicPublish(exchangeName, routingKey, null,
                    message.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            throw new RuntimeException("Error publishing event to RabbitMQ", e);
        }
    }
}