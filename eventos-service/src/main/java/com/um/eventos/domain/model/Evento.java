package com.um.eventos.domain.model;

import com.um.eventos.domain.exceptions.InvalidEventDataException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name="eventos")
public class Evento {
    @Id
    private final String id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private final String organizador;

    @Column(nullable = false)
    private int plazas;

    @Column(nullable = false)
    private boolean cancelado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaEvento categoria;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ocupacion_id", referencedColumnName = "id")
    private Ocupacion ocupacion;


    public Evento(String id, String organizador) {
        this.id = id;
        this.organizador = organizador;
    }

    @Override
    public Evento clone() {
        try {
            return (Evento) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clonación no soportada", e);
        }
    }


    public Evento modificar(String descripcion, LocalDateTime fechaInicio,
                            LocalDateTime fechaFin, Integer plazas, EspacioFisico espacio) {
        validarModificacion(fechaInicio, fechaFin, plazas, espacio);

        return Evento.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(descripcion != null ? descripcion : this.descripcion)
                .organizador(this.organizador)
                .plazas(plazas != null ? plazas : this.plazas)
                .cancelado(this.cancelado)
                .categoria(this.categoria)
                .ocupacion(actualizarOcupacion(fechaInicio, fechaFin, espacio))
                .build();
    }

    public Evento cancelar() {
        if (this.cancelado) {
            throw new IllegalStateException("El evento ya está cancelado");
        }

        return Evento.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .organizador(this.organizador)
                .plazas(this.plazas)
                .cancelado(true)
                .categoria(this.categoria)
                .ocupacion(this.ocupacion != null ?
                        Ocupacion.builder()
                                .id(this.ocupacion.getId())
                                .fechaInicio(this.ocupacion.getFechaInicio())
                                .fechaFin(this.ocupacion.getFechaFin())
                                .espacio(this.ocupacion.getEspacio())
                                .estado(false)
                                .build() : null)
                .build();
    }

    private void validarModificacion(LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                     Integer plazas, EspacioFisico espacio) {
        if (fechaInicio != null && fechaInicio.isBefore(LocalDateTime.now())) {
            throw new InvalidEventDataException("Fecha de inicio no puede ser en el pasado");
        }

        if (fechaFin != null && fechaFin.isBefore(fechaInicio != null ? fechaInicio : this.ocupacion.getFechaInicio())) {
            throw new InvalidEventDataException("Fecha de fin no puede ser antes de la de inicio");
        }

        if (plazas != null && plazas <= 0) {
            throw new InvalidEventDataException("El número de plazas debe ser positivo");
        }

        if (espacio != null && plazas != null && plazas > espacio.getCapacidad()) {
            throw new InvalidEventDataException("Plazas exceden capacidad del espacio");
        }
    }

    private Ocupacion actualizarOcupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacio) {
        return null;
    }

    public void setId(String id) {
    }
}