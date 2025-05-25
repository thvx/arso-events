package com.um.reservas.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponseDTO {
    private String id;
    private String idUsuario;
    private int plazasReservadas;
    private boolean cancelada;
    private String eventoId;
}