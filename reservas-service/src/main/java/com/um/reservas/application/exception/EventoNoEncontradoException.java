package com.um.reservas.application.exception;

public class EventoNoEncontradoException extends ApplicationException {
    public EventoNoEncontradoException(String eventoId) {
        super("Evento no encontrado con ID: " + eventoId, "EVENTO_NOT_FOUND");
    }
}