package com.um.reservas.domain.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCreadaEvent {
    private String reservaId;
    private String eventoId;
    private String usuarioId;
    private int plazasReservadas;
}