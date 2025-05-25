package com.um.reservas.application.exception;

public class ReservaNoEncontradaException extends ApplicationException {
    public ReservaNoEncontradaException(String reservaId) {
        super("Reserva no encontrada con ID: " + reservaId, "RESERVA_NOT_FOUND");
    }
}