package com.um.reservas.infrastructure.output.persistence;

import com.um.reservas.application.ports.output.EventoRepositoryPort;
import com.um.reservas.domain.model.Evento;
import com.um.reservas.infrastructure.output.persistence.entity.EventoDocument;
import com.um.reservas.infrastructure.output.persistence.mapper.EventoEntityMapper;
import com.um.reservas.infrastructure.output.persistence.repository.EventoMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventoPersistenceAdapter implements EventoRepositoryPort {

    private final EventoMongoRepository eventoMongoRepository;
    private final EventoEntityMapper eventoEntityMapper;

    @Override
    public Optional<Evento> findById(String id) {
        return eventoMongoRepository.findById(id)
                .map(eventoEntityMapper::toDomain);
    }

    @Override
    public Evento save(Evento evento) {
        EventoDocument documento = eventoEntityMapper.toEntity(evento);
        EventoDocument saved = eventoMongoRepository.save(documento);
        return eventoEntityMapper.toDomain(saved);
    }
}