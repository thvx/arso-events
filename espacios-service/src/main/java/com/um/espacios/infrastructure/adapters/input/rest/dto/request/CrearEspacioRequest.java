package com.um.espacios.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class CrearEspacioRequest {
    @NotBlank
    private String nombre;

    @NotBlank
    private String propietario;

    @Positive
    private int capacidad;

    @NotNull
    private UbicacionRequest ubicacion;

    private String descripcion;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UbicacionRequest {
        @NotNull
        private Double longitud;

        @NotNull
        private Double latitud;

        @NotBlank
        private String direccion;
    }
}