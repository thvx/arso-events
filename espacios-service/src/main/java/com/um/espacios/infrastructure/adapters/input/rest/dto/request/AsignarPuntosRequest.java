package com.um.espacios.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class AsignarPuntosRequest {
    @NotEmpty
    private List<PuntoDeInteresRequest> puntos;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PuntoDeInteresRequest {
        @NotBlank
        private String nombre;

        private String descripcion;

        @Positive
        private double distancia;

        private String urlWikipedia;
    }
}