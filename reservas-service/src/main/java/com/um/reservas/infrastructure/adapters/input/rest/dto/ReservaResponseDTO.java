package com.um.reservas.application.dto;

import com.um.reservas.domain.model.Reserva;
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
    private String eventoId;
    private String usuarioId;
    private int plazasReservadas;
    private boolean cancelada;

    public ReservaResponseDTO(Reserva reserva) {
    }
}