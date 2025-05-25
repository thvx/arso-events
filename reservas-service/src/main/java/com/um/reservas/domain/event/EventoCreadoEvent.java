package com.um.reservas.domain.event;

import com.um.reservas.domain.model.Evento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventoCreadoEvent {
    private String eventoId;

}