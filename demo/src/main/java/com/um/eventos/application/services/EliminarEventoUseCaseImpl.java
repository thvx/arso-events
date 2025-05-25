package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.EliminarEventoUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.exceptions.EntidadNoEncontradaException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EliminarEventoUseCaseImpl implements EliminarEventoUseCase {
    private final EventoRepository eventoRepository;

    @Inject
    public EliminarEventoUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public void eliminarEvento(String eventoId) throws EntidadNoEncontradaException{

        if (eventoRepository.buscarPorId(eventoId).isEmpty()) throw new EntidadNoEncontradaException("El evento no existe");

        eventoRepository.eliminar(eventoId);
    }
}