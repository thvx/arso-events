package com.um.espacios.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ubicacion {
    private double longitud;
    private double latitud;
    private String direccion; //Dirección Postal TODO: Regex para validar dirección postal

}