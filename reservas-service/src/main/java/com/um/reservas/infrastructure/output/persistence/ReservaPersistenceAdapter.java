package com.um.reservas.infrastructure.output.persistence;

import com.um.reservas.application.ports.output.ReservaRepositoryPort;
import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.model.Reserva;
import com.um.reservas.infrastructure.output.persistence.entity.EventoDocument;
import com.um.reservas.infrastructure.output.persistence.entity.ReservaDocument;
import com.um.reservas.infrastructure.output.persistence.mapper.EventoEntityMapperImpl;
import com.um.reservas.infrastructure.output.persistence.mapper.ReservaEntityMapper;
import com.um.reservas.infrastructure.output.persistence.repository.ReservaMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservaPersistenceAdapter implements ReservaRepositoryPort {

    private final ReservaMongoRepository reservaMongoRepository;
    private final ReservaEntityMapper reservaEntityMapper;
    private final EventoPersistenceAdapter eventoPersistenceAdapter;

    @Override
    public Optional<Reserva> findById(String id) {
        return reservaMongoRepository.findById(id)
                .map(document -> {
                    Evento evento = eventoPersistenceAdapter.findById(document.getEvento().getId())
                            .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
                    return reservaEntityMapper.toDomainWithEvento(document, evento);
                });
    }

    @Override
    public List<Reserva> findByEventoId(String eventoId) {
        return reservaMongoRepository.findByEventoId(eventoId).stream()
                .map(document -> {
                    Evento evento = eventoPersistenceAdapter.findById(eventoId)
                            .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
                    return reservaEntityMapper.toDomainWithEvento(document, evento);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Reserva save(Reserva reserva) {
        EventoDocument eventoDocument = eventoPersistenceAdapter.findById(reserva.getEvento().getId())
                .map(evento -> {
                    EventoDocument doc = new EventoEntityMapperImpl().toEntity(evento);
                    doc.setId(evento.getId());
                    return doc;
                })
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        ReservaDocument document = reservaEntityMapper.toEntityWithEvento(reserva, eventoDocument);
        ReservaDocument saved = reservaMongoRepository.save(document);

        Evento evento = new EventoEntityMapperImpl().toDomain(eventoDocument);
        return reservaEntityMapper.toDomainWithEvento(saved, evento);
    }
}