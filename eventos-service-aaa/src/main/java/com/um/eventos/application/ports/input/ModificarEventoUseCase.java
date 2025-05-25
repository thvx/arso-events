package com.um.eventos.application.ports.input;

import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.exceptions.EspacioNoDisponibleException;
import com.um.eventos.domain.model.Evento;

import java.time.LocalDateTime;

public interface ModificarEventoUseCase {
    Evento modificarEvento(String id, String descripcion, LocalDateTime fechaInicio,
                           LocalDateTime fechaFin, Integer plazas, String espacioId) throws EntidadNoEncontradaException, EspacioNoDisponibleException;

    void cancelarEvento(String id) throws EntidadNoEncontradaException;
}