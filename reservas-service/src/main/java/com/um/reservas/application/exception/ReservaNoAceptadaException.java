package com.um.reservas.application.exception;

public class ReservaNoAceptadaException extends ApplicationException {
    public ReservaNoAceptadaException(String message) {
        super(message, "RESERVA_NOT_ACCEPTED");
    }
}