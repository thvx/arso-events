package com.um.reservas.application.ports.output;

import com.um.reservas.domain.model.Evento;

import java.util.Optional;

public interface EventoRepositoryPort {
    Optional<Evento> findById(String id);
    Evento save(Evento evento);
}