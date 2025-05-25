package com.um.eventos.domain.events;

import com.um.eventos.domain.model.Evento;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventoCreadoEvent implements DomainEvent {
    private final String eventId;
    private final LocalDateTime occurredOn;
    @Getter
    private final Evento evento;

    public EventoCreadoEvent(Evento evento) {
        this.eventId = generateEventId();
        this.occurredOn = LocalDateTime.now();
        this.evento = evento;
    }

    @Override
    public String getEventType() {
        return "evento-creado";
    }

    @Override
    public String getAggregateId() {
        return evento.getId();
    }
}