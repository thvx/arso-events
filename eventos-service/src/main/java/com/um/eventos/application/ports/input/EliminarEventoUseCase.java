package com.um.eventos.application.ports.input;

import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;

public interface EliminarEventoUseCase {
    void eliminarEvento(String eventoId) throws EntidadNoEncontradaException;
}