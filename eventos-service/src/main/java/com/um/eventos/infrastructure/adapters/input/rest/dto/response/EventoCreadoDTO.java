package com.um.eventos.infrastructure.adapters.input.rest.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventoCreadoDTO {
    private String id;
    private LocalDateTime fechaConfirmacion;

    public EventoCreadoDTO(String id, LocalDateTime fechaConfirmacion) {
        this.id = id;
        this.fechaConfirmacion = fechaConfirmacion;
    }

}