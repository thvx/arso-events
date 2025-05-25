package com.um.eventos.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EspacioFisico {
    @EqualsAndHashCode.Include
    private String id;
    private String nombre;
    private int capacidad;
    private EstadoEspacio estado;

    @Builder
    public EspacioFisico(String id, String nombre, int capacidad, EstadoEspacio estado) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NullPointerException("El nombre es requerido");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public enum EstadoEspacio {
        ACTIVO,
        CERRADO_TEMPORALMENTE
    }
}