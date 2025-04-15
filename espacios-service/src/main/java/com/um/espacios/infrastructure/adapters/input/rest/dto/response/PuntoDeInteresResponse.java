package com.um.espacios.infrastructure.adapters.input.rest.dto.response;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class PuntoDeInteresResponse {
    private String nombre;
    private String descripcion;
    private double distancia;
    private String urlWikipedia;
}