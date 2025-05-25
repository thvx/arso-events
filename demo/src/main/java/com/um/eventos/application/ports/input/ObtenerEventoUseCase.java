package com.um.eventos.application.ports.input;

import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.model.Evento;

public interface ObtenerEventoUseCase {
    Evento obtenerEvento(String id) throws EntidadNoEncontradaException;
}