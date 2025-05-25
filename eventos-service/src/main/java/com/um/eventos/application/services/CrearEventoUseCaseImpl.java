package com.um.eventos.application.services;

import com.um.eventos.application.ports.input.CrearEventoUseCase;
import com.um.eventos.application.ports.input.VerificarDisponibilidadUseCase;
import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.application.ports.output.EventPublisherPort;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.events.EventoCreadoEvent;
import com.um.eventos.domain.exceptions.*;
import com.um.eventos.domain.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;


@ApplicationScoped
public class CrearEventoUseCaseImpl implements CrearEventoUseCase {
    private final EventoRepository eventoRepository;
    private final EspacioServicePort espacioService;
    private final VerificarDisponibilidadUseCase verificarDisponibilidadUseCase;
    private final EventPublisherPort eventPublisher;

    @Inject
    public CrearEventoUseCaseImpl(EventoRepository eventoRepository,
                                  EspacioServicePort espacioService,
                                  VerificarDisponibilidadUseCase verificarDisponibilidadUseCase,
                                  EventPublisherPort eventPublisher) {
        this.eventoRepository = eventoRepository;
        this.espacioService = espacioService;
        this.verificarDisponibilidadUseCase = verificarDisponibilidadUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Evento crearEvento(String nombre, String descripcion, String organizador,
                              int plazas, CategoriaEvento categoria,
                              LocalDateTime fechaInicio, LocalDateTime fechaFin,
                              String espacioId) throws EspacioNoDisponibleException, EntidadNoEncontradaException {

        validarDatos(nombre, descripcion, organizador, plazas, categoria, fechaInicio, fechaFin, espacioId);

        EspacioFisico espacio = obtenerYValidarEspacio(espacioId, plazas, fechaInicio, fechaFin);

        Ocupacion ocupacion = Ocupacion.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .espacio(espacio)
                .estado(true)
                .build();

        Evento evento = Evento.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .organizador(organizador)
                .plazas(plazas)
                .cancelado(false)
                .categoria(categoria)
                .ocupacion(ocupacion)
                .build();

        Evento eventoCreado = eventoRepository.guardar(evento);
        eventPublisher.publishEvent(new EventoCreadoEvent(eventoCreado));
        return eventoCreado;
    }

    private void validarDatos(String nombre, String descripcion, String organizador,
                              int plazas, CategoriaEvento categoria,
                              LocalDateTime fechaInicio, LocalDateTime fechaFin,
                              String espacioId) {
        if (nombre == null || nombre.isEmpty()) {
            throw new InvalidEventDataException("Nombre es requerido");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new InvalidEventDataException("Descripción es requerida");
        }
        if (organizador == null || organizador.isEmpty()) {
            throw new InvalidEventDataException("Organizador es requerido");
        }
        if (plazas <= 0) {
            throw new InvalidEventDataException("El número de plazas debe ser positivo");
        }
        if (categoria == null) {
            throw new InvalidEventDataException("Categoría es requerida");
        }
        if (fechaInicio == null || fechaFin == null) {
            throw new InvalidEventDataException("Fechas son requeridas");
        }
        if (fechaInicio.isBefore(LocalDateTime.now())) {
            throw new InvalidEventDataException("Fecha de inicio no puede ser en el pasado");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new InvalidEventDataException("Fecha de fin no puede ser antes de la de inicio");
        }
        if (espacioId == null || espacioId.isEmpty()) {
            throw new InvalidEventDataException("Espacio es requerido");
        }
    }

    private EspacioFisico obtenerYValidarEspacio(String espacioId, int plazas,
                                                 LocalDateTime fechaInicio, LocalDateTime fechaFin)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException {

        EspacioFisico espacio = espacioService.obtenerEspacio(espacioId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Espacio no encontrado"));

        if (espacio.getEstado() != EspacioFisico.EstadoEspacio.ACTIVO) {
            throw new EspacioNoDisponibleException("Espacio no está activo");
        }

        if (plazas > espacio.getCapacidad()) {
            throw new EspacioNoDisponibleException("Plazas exceden capacidad del espacio");
        }

        if (!verificarDisponibilidadUseCase.verificarDisponibilidad(
                espacioId, fechaInicio, fechaFin, plazas)) {
            throw new EspacioNoDisponibleException("El espacio no está disponible en las fechas solicitadas");
        }

        return espacio;
    }
}