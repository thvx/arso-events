package com.um.reservas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearReservaRequestDTO {
    private String eventoId;
    private String usuarioId;
    private int plazas;
}