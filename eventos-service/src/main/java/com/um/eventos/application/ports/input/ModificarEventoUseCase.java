package com.um.eventos.application.ports.input;

import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.exceptions.EspacioNoDisponibleException;

import java.time.LocalDateTime;

public interface ModificarEventoUseCase {
    void modificarEvento(String id, String descripcion, LocalDateTime fechaInicio,
                         LocalDateTime fechaFin, int plazas, String espacioId) throws EntidadNoEncontradaException, EspacioNoDisponibleException;

    void cancelarEvento(String id) throws EntidadNoEncontradaException;
}