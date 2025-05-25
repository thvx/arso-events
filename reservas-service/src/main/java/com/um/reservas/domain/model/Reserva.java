package com.um.reservas.domain.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    private String id;
    private String idUsuario;
    private int plazasReservadas;
    private boolean cancelada;
    private Evento evento;

}