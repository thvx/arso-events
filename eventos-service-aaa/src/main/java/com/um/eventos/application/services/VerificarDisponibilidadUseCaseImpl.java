package com.um.eventos.application.services;

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
    public boolean verificarDisponibilidad(String espacioId, LocalDateTime fechaInicio,
                                           LocalDateTime fechaFin, int plazas) {
        validarDatosEntrada(espacioId, fechaInicio, fechaFin, plazas);

        List<Evento> eventos = eventoRepository.buscarEventosEnEspacioEntreFechas(
                espacioId, fechaInicio, fechaFin, true);

        return eventos.stream()
                .noneMatch(e -> e.getOcupacion() != null &&
                        e.getOcupacion().isEstado() &&
                        !e.isCancelado());
    }

    private void validarDatosEntrada(String espacioId, LocalDateTime fechaInicio,
                                     LocalDateTime fechaFin, int plazas) {
        if (espacioId == null || espacioId.isEmpty()) {
            throw new InvalidEventDataException("ID de espacio no puede ser nulo o vacío");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new InvalidEventDataException("Fechas no pueden ser nulas");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new InvalidEventDataException("Fecha de fin no puede ser anterior a la de inicio");
        }
        if (plazas <= 0) {
            throw new InvalidEventDataException("El número de plazas debe ser positivo");
        }
    }
}