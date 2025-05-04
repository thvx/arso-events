package com.um.eventos.application.service;

import com.um.eventos.application.ports.input.ObtenerEventoUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ObtenerEventoUseCaseImpl implements ObtenerEventoUseCase {
    private final EventoRepository eventoRepository;

    @Inject
    public ObtenerEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public Evento obtenerEvento(String id) throws EntidadNoEncontradaException {
        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Evento no encontrado"));
    }
}