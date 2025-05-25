package com.um.eventos.infrastructure.adapters.input.rest.dto.response;

import com.um.eventos.domain.model.CategoriaEvento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoResponseDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private String organizador;
    private CategoriaEvento categoria;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private int plazas;
    private String espacioId;
    private boolean cancelado;
    private String espacioNombre;
    private String espacioDireccion;

}