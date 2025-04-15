package com.um.espacios.infrastructure.adapters.output.persistence.entitiy;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoDeInteresDocument {
    private String nombre;
    private String descripcion;
    private double distancia;
    private String urlWikipedia;
}