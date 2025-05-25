package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ObtenerEventosPorEspacioUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class ObtenerEventosPorEspacioUseCaseImpl implements ObtenerEventosPorEspacioUseCase {
    private final EventoRepository eventoRepository;

    @Inject
    public ObtenerEventosPorEspacioUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> obtenerEventosPorEspacio(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del espacio no puede ser nulo o vacío");
        }
        return eventoRepository.buscarEventosEnEspacio(id, true);
    }
}