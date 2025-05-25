package com.um.reservas.application.ports.output;

public interface EventPublisherPort {
    void publishEvent(String routingKey, Object event);
}