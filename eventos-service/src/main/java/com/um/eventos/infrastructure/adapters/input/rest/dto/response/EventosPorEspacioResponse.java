package com.um.eventos.infrastructure.adapters.input.rest.dto.response;

import com.um.eventos.domain.model.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventosPorEspacioResponse {
    private String id;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;

    public EventosPorEspacioResponse(List<Evento> eventos) {
    }
}