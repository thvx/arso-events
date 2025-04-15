package com.um.espacios.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PuntoDeInteres {
    private String nombre;
    private String descripcion;
    private double distancia;
    private String urlWikipedia;
}