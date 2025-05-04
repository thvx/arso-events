package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ResumenEventosUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class ResumenEventosUseCaseImpl implements ResumenEventosUseCase {

    private final EventoRepository eventoRepository;

    @Inject
    public ResumenEventosUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public List<Evento> obtenerEventosPorMes(String mes, String anio) {
        return eventoRepository.buscarPorFecha(mes, anio);
    }
}