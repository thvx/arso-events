package com.um.espacios.domain.exceptions;

public class ServicioNoDisponibleException extends Throwable {
    public ServicioNoDisponibleException(String mensaje, Exception e) {
        super(mensaje);
    }
}