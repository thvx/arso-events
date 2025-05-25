package com.um.eventos.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "espacios_fisicos")
public class EspacioFisico {
    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(nullable = false)
    private int capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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