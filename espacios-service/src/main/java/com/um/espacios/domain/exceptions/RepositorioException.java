package com.um.espacios.domain.exceptions;

public class RepositorioException extends Exception {
    public RepositorioException(String mensaje, Exception e) {
        super(mensaje);
    }
}