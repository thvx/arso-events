package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ListarTodosEventosUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ListarTodosEventosUseCaseImpl implements ListarTodosEventosUseCase {

    private final EventoRepository eventoRepository;

    @Inject
    public ListarTodosEventosUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> listarTodosEventos() {
        return eventoRepository.listarTodos();
    }
}