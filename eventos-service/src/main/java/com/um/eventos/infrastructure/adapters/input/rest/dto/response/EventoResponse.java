package com.um.eventos.infrastructure.adapters.input.rest.dto.response;

import com.um.eventos.domain.model.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventoResponse {
    private String id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String categoria;
    private String espacioId;
    private int plazas;
    private String estado;

    public EventoResponse(Evento evento) {
    }
}