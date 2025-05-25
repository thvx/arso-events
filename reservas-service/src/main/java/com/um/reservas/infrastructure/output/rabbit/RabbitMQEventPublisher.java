package com.um.reservas.infrastructure.output.rabbit;
import com.um.reservas.application.ports.output.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements EventPublisherPort {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String reservaCreadaRoutingKey;
    private final String reservaCanceladaRoutingKey;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate,
                                         @Value("${rabbitmq.exchange.name}") String exchange,
                                         @Value("${rabbitmq.routing.reserva-creada.key}") String reservaCreadaRoutingKey,
                                         @Value("${rabbitmq.routing.reserva-cancelada.key}") String reservaCanceladaRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.reservaCreadaRoutingKey = reservaCreadaRoutingKey;
        this.reservaCanceladaRoutingKey = reservaCanceladaRoutingKey;
    }

    @Override
    public void publishEvent(String routingKey, Object event) {
        if (routingKey.equals(reservaCreadaRoutingKey)) {
            rabbitTemplate.convertAndSend(exchange, reservaCreadaRoutingKey, event);
        } else if (routingKey.equals(reservaCanceladaRoutingKey)) {
            rabbitTemplate.convertAndSend(exchange, reservaCanceladaRoutingKey, event);
        }
    }
}