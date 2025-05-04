package com.um.eventos.domain.exceptions;

public class ApiIntegrationException extends RuntimeException {
    public ApiIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}