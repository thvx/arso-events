package com.um.espacios.infrastructure.adapters.input.rest.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class EspacioResponse {
    private String id;
    private String nombre;
    private String propietario;
    private int capacidad;
    private UbicacionResponse ubicacion;
    private List<PuntoDeInteresResponse> puntosDeInteres;
    private String descripcion;
    private String estado;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UbicacionResponse {
        private Double longitud;
        private Double latitud;
        private String direccion;
    }
}