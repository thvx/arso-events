package com.um.reservas.infrastructure.persistence.repository;
import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.ports.EventoRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EventoMongoRepository implements EventoRepositoryPort {
    private final SpringDataEventoRepository repository;

    public EventoMongoRepository(SpringDataEventoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Evento> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Evento save(Evento evento) {
        return repository.save(evento);
    }
}