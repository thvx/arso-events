package com.um.espacios.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@AllArgsConstructor
public class Ocupacion {

    @EqualsAndHashCode.Include
    private final String id;
    private final LocalDateTime fechaInicio;
    private final LocalDateTime fechaFin;
    private final EspacioFisico espacio;
    private boolean cancelada;

    public boolean estaActiva() {
        return !cancelada && fechaFin.isAfter(LocalDateTime.now());
    }
}