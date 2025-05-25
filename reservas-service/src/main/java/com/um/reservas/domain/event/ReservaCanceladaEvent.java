package com.um.reservas.domain.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCanceladaEvent {
    private String reservaId;
    private String eventoId;
    private int plazasLiberadas;
}