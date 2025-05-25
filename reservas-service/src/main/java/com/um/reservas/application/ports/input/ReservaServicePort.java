package com.um.reservas.application.ports.input;

import com.um.reservas.domain.model.Reserva;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReservaServicePort {
    String realizarReserva(String eventoId, String usuarioId, int plazas);

    @Transactional
    void cancelarReserva(String reservaId);

    Reserva obtenerReserva(String id);
    List<Reserva> obtenerReservasPorEvento(String eventoId);
}