package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.EliminarEventoUseCase;
import com.um.eventos.application.ports.output.EventPublisherPort;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.events.EventoCanceladoEvent;
import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EliminarEventoUseCaseImpl implements EliminarEventoUseCase {
    private final EventoRepository eventoRepository;
    private final EventPublisherPort eventPublisher;

    @Inject
    public EliminarEventoUseCaseImpl(EventoRepository eventoRepository, EventPublisherPort eventPublisher) {
        this.eventoRepository = eventoRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void eliminarEvento(String eventoId) throws EntidadNoEncontradaException {
        Evento evento = eventoRepository.buscarPorId(eventoId)
                .orElseThrow(() -> new EntidadNoEncontradaException("El evento no existe"));

        // Publicar evento antes de eliminar
        eventPublisher.publishEvent(new EventoCanceladoEvent(evento, "Evento eliminado"));

        eventoRepository.eliminar(eventoId);
    }
}