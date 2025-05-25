package com.um.eventos.application.ports.output;

import com.um.eventos.domain.events.DomainEvent;

public interface EventPublisherPort {
    void publishEvent(DomainEvent event);
}