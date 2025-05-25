package com.um.reservas.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EventoCanceladoEvent {
    private String eventoId;
}