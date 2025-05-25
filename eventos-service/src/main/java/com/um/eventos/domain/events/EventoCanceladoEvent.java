package com.um.eventos.domain.events;

import com.um.eventos.domain.model.Evento;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventoCanceladoEvent implements DomainEvent {
    private final String eventId;
    private final LocalDateTime occurredOn;
    private final Evento evento;
    private final String motivo;

    public EventoCanceladoEvent(Evento evento, String motivo) {
        this.eventId = generateEventId();
        this.occurredOn = LocalDateTime.now();
        this.evento = evento;
        this.motivo = motivo;
    }

    @Override
    public String getEventType() {
        return "evento-cancelado";
    }

    @Override
    public String getAggregateId() {
        return evento.getId();
    }
}