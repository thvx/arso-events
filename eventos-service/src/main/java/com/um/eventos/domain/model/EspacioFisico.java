package com.um.eventos.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
public class EspacioFisico {
    private String id;
    private String nombre;
    private int capacidad;
    private EstadoEspacio estado;

    public enum EstadoEspacio {
        ACTIVO,
        CERRADO_TEMPORALMENTE
    }
}