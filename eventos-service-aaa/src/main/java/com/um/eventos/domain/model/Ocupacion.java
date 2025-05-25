package com.um.eventos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = false)
@Entity
@Table(name = "ocupaciones")
public class Ocupacion {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private EspacioFisico espacio;

    @Column(nullable = false)
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
