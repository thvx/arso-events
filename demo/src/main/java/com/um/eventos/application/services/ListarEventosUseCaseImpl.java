package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ListarEventosUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class ListarEventosUseCaseImpl implements ListarEventosUseCase {

    final EventoRepository eventoRepository;

    @Inject
    public ListarEventosUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> listarEventos(){
        return eventoRepository.listarEventosActivos();
    }
}