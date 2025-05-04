package com.um.eventos.application.service;

import com.um.eventos.application.ports.input.VerificarDisponibilidadUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.exceptions.InvalidEventDataException;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;


@ApplicationScoped
public class VerificarDisponibilidadUseCaseImpl implements VerificarDisponibilidadUseCase {

    private final EventoRepository eventoRepository;

    @Inject
    public VerificarDisponibilidadUseCaseImpl(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Override
    public boolean verificarDisponibilidad(String espacioId, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas) {
        if (espacioId == null || espacioId.isEmpty()) {
            throw new InvalidEventDataException("ID de espacio no puede ser nulo o vacío");
        }

        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            throw new InvalidEventDataException("Fechas inválidas");
        }

        List<Evento> eventos = eventoRepository.buscarEventosEnEspacioEntreFechas(
                espacioId, fechaInicio, fechaFin, true);

        // El espacio está disponible si no hay eventos activos en ese período
        return eventos.stream()
                .noneMatch(evento -> !evento.isCancelado() &&
                        evento.getOcupacion() != null &&
                        evento.getOcupacion().isEstado());
    }

}