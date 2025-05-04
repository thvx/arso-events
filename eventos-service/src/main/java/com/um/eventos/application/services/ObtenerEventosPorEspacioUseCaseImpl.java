package com.um.eventos.application.service;

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
    public List<Evento> obtenerEventosPorEspacio(String id){
        return eventoRepository.buscarEventosEnEspacio(id, true);
    }
}