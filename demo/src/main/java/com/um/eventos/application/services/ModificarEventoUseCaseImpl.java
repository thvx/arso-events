package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ModificarEventoUseCase;
import com.um.eventos.application.ports.input.VerificarDisponibilidadUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.exceptions.*;
import com.um.eventos.domain.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@ApplicationScoped
public class ModificarEventoUseCaseImpl implements ModificarEventoUseCase {

    private final EventoRepository eventoRepository;
    private final EspacioServicePort espacioService;
    private final VerificarDisponibilidadUseCase verificarDisponibilidadUseCase;


    @Inject
    public ModificarEventoUseCaseImpl(EventoRepository eventoRepository,
                                      EspacioServicePort espacioService,
                                      VerificarDisponibilidadUseCase verificarDisponibilidadUseCase) {
        this.eventoRepository = eventoRepository;
        this.espacioService = espacioService;
        this.verificarDisponibilidadUseCase = verificarDisponibilidadUseCase;
    }

    @Override
    public Evento modificarEvento(String id, String descripcion, LocalDateTime fechaInicio,
                                  LocalDateTime fechaFin, Integer plazas, String espacioId)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException, InvalidEventDataException {

        Evento evento = obtenerEvento(id);

        EspacioFisico espacio = null;
        if (espacioId != null) {
            espacio = obtenerYValidarEspacio(
                    espacioId,
                    plazas != null ? plazas : evento.getPlazas(),
                    fechaInicio != null ? fechaInicio : evento.getOcupacion().getFechaInicio(),
                    fechaFin != null ? fechaFin : evento.getOcupacion().getFechaFin()
            );
        }

        Evento eventoModificado = evento.modificar(
                descripcion,
                fechaInicio,
                fechaFin,
                plazas,
                espacio
        );

        eventoRepository.actualizar(id, eventoModificado);
        return evento;
    }

    @Override
    public void cancelarEvento(String eventoId) throws EntidadNoEncontradaException {
        Evento evento = obtenerEvento(eventoId);
        Evento eventoCancelado = evento.cancelar();
        eventoRepository.actualizar(eventoId, eventoCancelado);
    }

    private Evento obtenerEvento(String eventoId) throws EntidadNoEncontradaException {
        return eventoRepository.buscarPorId(eventoId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Evento no encontrado"));
    }

    private EspacioFisico obtenerYValidarEspacio(String espacioId, int plazas,
                                                 LocalDateTime fechaInicio, LocalDateTime fechaFin)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException {

        EspacioFisico espacio = espacioService.obtenerEspacioPorId(espacioId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Espacio no encontrado"));

        if (espacio.getEstado() != EspacioFisico.EstadoEspacio.ACTIVO) {
            throw new EspacioNoDisponibleException("Espacio no está activo");
        }

        if (!verificarDisponibilidadUseCase.verificarDisponibilidad(
                espacioId, fechaInicio, fechaFin, plazas)) {
            throw new EspacioNoDisponibleException("Espacio no disponible en esas fechas");
        }

        return espacio;
    }
}