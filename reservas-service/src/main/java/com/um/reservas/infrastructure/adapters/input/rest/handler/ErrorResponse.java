package com.um.reservas.infrastructure;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private Instant timestamp;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }
}