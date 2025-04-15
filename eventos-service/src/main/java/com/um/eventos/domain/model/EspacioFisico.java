package com.um.eventos.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioFisico {
    private String id;
    private String nombre;
    private String direccion;
    private int capacidad;
}
