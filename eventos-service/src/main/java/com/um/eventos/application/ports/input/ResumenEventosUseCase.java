package com.um.eventos.application.ports.input;

import com.um.eventos.domain.model.Evento;

import java.util.List;

public interface ResumenEventosUseCase {
    List<Evento> obtenerEventosPorMes(String mes, String anio);
}