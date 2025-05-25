package com.um.reservas.application.ports.output;

import com.um.reservas.domain.model.Reserva;

import java.util.List;
import java.util.Optional;

public interface ReservaRepositoryPort {
    Optional<Reserva> findById(String id);
    List<Reserva> findByEventoId(String eventoId);
    Reserva save(Reserva reserva);
}
