package com.um.eventos.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = false)
public class Ocupacion {
    @EqualsAndHashCode.Include
    private String id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EspacioFisico espacio;
    @Builder.Default
    private boolean estado = true;

    @Builder
    public Ocupacion(String id, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                     EspacioFisico espacio, Boolean estado) {
        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.espacio = espacio;
        this.estado = estado != null ? estado : true;
    }
}
