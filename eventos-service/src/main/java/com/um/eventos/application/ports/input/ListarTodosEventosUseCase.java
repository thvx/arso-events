package com.um.eventos.application.ports.input;

import com.um.eventos.domain.model.Evento;

import java.util.List;

public interface ListarTodosEventosUseCase {
    List<Evento> listarTodosEventos();
}