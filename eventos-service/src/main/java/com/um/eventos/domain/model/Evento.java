package com.um.eventos.domain.model;

import com.um.eventos.domain.exceptions.InvalidEventDataException;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
    @Builder
    public class Evento {
        private final String id;
        private String nombre;
        private String descripcion;
        private final String organizador;
        private int plazas;
        private boolean cancelado;
        private CategoriaEvento categoria;
        private Ocupacion ocupacion;

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

    public void cancelar() {
        if (this.cancelado) {
            throw new IllegalStateException("El evento ya está cancelado");
        }

        Evento.builder()
                .id(this.id)
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .organizador(this.organizador)
                .plazas(this.plazas)
                .cancelado(true)
                .categoria(this.categoria)
                .ocupacion(null)
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

        if (plazas != null && espacio != null && plazas > espacio.getCapacidad()) {
            throw new InvalidEventDataException("Plazas exceden capacidad del espacio");
        }
    }

    private Ocupacion actualizarOcupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacio) {
        if (fechaInicio == null && fechaFin == null && espacio == null) {
            return this.ocupacion;
        }

        return Ocupacion.builder()
                .id(this.ocupacion != null ? this.ocupacion.getId() : null)
                .fechaInicio(fechaInicio != null ? fechaInicio : this.ocupacion.getFechaInicio())
                .fechaFin(fechaFin != null ? fechaFin : this.ocupacion.getFechaFin())
                .espacio(espacio != null ? espacio : this.ocupacion.getEspacio())
                .build();
    }

}