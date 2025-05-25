package com.um.eventos.application.ports.input;

import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.exceptions.EspacioNoDisponibleException;
import com.um.eventos.domain.model.CategoriaEvento;
import com.um.eventos.domain.model.Evento;

import java.time.LocalDateTime;

public interface CrearEventoUseCase {
    Evento crearEvento(String nombre, String descripcion, String organizador,
                       int plazas, CategoriaEvento categoria,
                       LocalDateTime fechaInicio, LocalDateTime fechaFin,
                       String espacioId) throws EspacioNoDisponibleException, EntidadNoEncontradaException;
}