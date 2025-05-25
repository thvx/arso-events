package com.um.eventos.domain.exceptions;

public class EntidadNoEncontradaException extends EventoException {
    public EntidadNoEncontradaException(String id) {
        super("Entidad con ID: " + id + " no encontrada");
    }
}