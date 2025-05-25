package com.um.espacios.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Component
public class AsignarPuntosRequest {
    @NotEmpty
    private List<PuntoDeInteresRequest> puntos;

    @Getter
    @Setter
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