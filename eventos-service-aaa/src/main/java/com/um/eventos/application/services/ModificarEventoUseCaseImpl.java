package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.ModificarEventoUseCase;
import com.um.eventos.application.ports.input.VerificarDisponibilidadUseCase;
import com.um.eventos.application.ports.output.EventPublisherPort;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.events.EventoCanceladoEvent;
import com.um.eventos.domain.events.EventoModificadoEvent;
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
    private final EventPublisherPort eventPublisher;

    @Inject
    public ModificarEventoUseCaseImpl(EventoRepository eventoRepository,
                                      EspacioServicePort espacioService,
                                      VerificarDisponibilidadUseCase verificarDisponibilidadUseCase,
                                      EventPublisherPort eventPublisher) {
        this.eventoRepository = eventoRepository;
        this.espacioService = espacioService;
        this.verificarDisponibilidadUseCase = verificarDisponibilidadUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Evento modificarEvento(String id, String descripcion, LocalDateTime fechaInicio,
                                  LocalDateTime fechaFin, Integer plazas, String espacioId)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException, InvalidEventDataException {

        Evento evento = obtenerEvento(id);
        Evento eventoAnterior = evento.clone(); // Guardar estado anterior para el evento

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
        eventPublisher.publishEvent(new EventoModificadoEvent(eventoModificado,
                "Modificación de evento: " + obtenerCambios(eventoAnterior, eventoModificado)));

        return eventoModificado;
    }

    @Override
    public void cancelarEvento(String eventoId) throws EntidadNoEncontradaException {
        Evento evento = obtenerEvento(eventoId);
        Evento eventoCancelado = evento.cancelar();
        eventoRepository.actualizar(eventoId, eventoCancelado);
        eventPublisher.publishEvent(new EventoCanceladoEvent(eventoCancelado, "Cancelado por el organizador"));
    }

    private String obtenerCambios(Evento anterior, Evento nuevo) {
        // Implementación para detectar cambios específicos
        StringBuilder cambios = new StringBuilder();
        if (!anterior.getDescripcion().equals(nuevo.getDescripcion())) {
            cambios.append("Descripción modificada, ");
        }
        if (!anterior.getOcupacion().getFechaInicio().equals(nuevo.getOcupacion().getFechaInicio())) {
            cambios.append("Fecha inicio modificada, ");
        }
        if (!anterior.getOcupacion().getFechaFin().equals(nuevo.getOcupacion().getFechaFin())) {
            cambios.append("Fecha fin modificada, ");
        }
        if (anterior.getPlazas() != nuevo.getPlazas()) {
            cambios.append("Número de plazas modificado, ");
        }
        return cambios.toString();
    }

    private Evento obtenerEvento(String eventoId) throws EntidadNoEncontradaException {
        return eventoRepository.buscarPorId(eventoId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Evento no encontrado"));
    }

    private EspacioFisico obtenerYValidarEspacio(String espacioId, int plazas,
                                                 LocalDateTime fechaInicio, LocalDateTime fechaFin)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException {

        EspacioFisico espacio = espacioService.obtenerEspacio(espacioId)
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