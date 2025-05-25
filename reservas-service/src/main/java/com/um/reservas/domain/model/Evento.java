package com.um.reservas.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Evento {
    private String id;
    private int plazasDisponibles;
    private boolean cancelado;
    private List<Reserva> reservas = new ArrayList<>();

    public boolean puedeAceptarReserva(int plazasSolicitadas) {
        return !cancelado && plazasDisponibles >= plazasSolicitadas;
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
        plazasDisponibles -= reserva.getPlazasReservadas();
    }
}