package com.um.eventos.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Ocupacion {

    private String id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private EspacioFisico espacio;
    private boolean estado;
}
