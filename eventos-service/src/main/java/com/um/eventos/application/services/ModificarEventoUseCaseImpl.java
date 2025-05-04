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
    public void modificarEvento(String id, String descripcion, LocalDateTime fechaInicio,
                                LocalDateTime fechaFin, int plazas, String espacioId)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException, InvalidEventDataException {

        Evento evento = obtenerEvento(id);

        // Validar que el evento tenga ocupación
        // if (evento.getOcupacion() == null) {
        //     throw new InvalidEventDataException("El evento no tiene una ocupación asignada");
        // }

        // Preparar valores para modificación
        String nuevaDescripcion = descripcion != null ? descripcion : evento.getDescripcion();
        LocalDateTime nuevaFechaInicio = fechaInicio != null ? fechaInicio : evento.getOcupacion().getFechaInicio();
        LocalDateTime nuevaFechaFin = fechaFin != null ? fechaFin : evento.getOcupacion().getFechaFin();
        int nuevasPlazas = plazas > 0 ? plazas : evento.getPlazas();

        // Validar fechas
        if (nuevaFechaFin.isBefore(nuevaFechaInicio)) {
            throw new InvalidEventDataException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Gestionar espacio físico
        EspacioFisico espacio;
        if (espacioId != null) {
            espacio = obtenerYValidarEspacio(
                    espacioId,
                    nuevasPlazas,
                    nuevaFechaInicio,
                    nuevaFechaFin
            );
        } else {
            // Si no se cambia el espacio, usar el existente
            espacio = evento.getOcupacion().getEspacio();
        }

        // Modificar el evento
        Evento eventoModificado = evento.modificar(
                nuevaDescripcion,
                nuevaFechaInicio,
                nuevaFechaFin,
                nuevasPlazas,
                espacio
        );

        eventoRepository.actualizar(id, eventoModificado);
    }

    @Override
    public void cancelarEvento(String eventoId) throws EntidadNoEncontradaException {
        if (eventoId == null || eventoId.isEmpty()) {
            throw new InvalidEventDataException("ID de evento no puede ser nulo o vacío");
        }

        Evento evento = obtenerEvento(eventoId);
        evento.cancelar();
        eventoRepository.actualizar(eventoId, evento);
    }

    private Evento obtenerEvento(String eventoId) throws EntidadNoEncontradaException {
        Evento evento = eventoRepository.buscarPorId(eventoId)
                .orElseThrow(() -> new EntidadNoEncontradaException(eventoId));

        if (evento.isCancelado()) {
            throw new InvalidEventDataException("Evento cancelado no puede modificarse");
        }
        return evento;
    }

    private EspacioFisico obtenerYValidarEspacio(String espacioId, int plazas,
                                                LocalDateTime fechaInicio, LocalDateTime fechaFin)
            throws EntidadNoEncontradaException, EspacioNoDisponibleException {

        if (espacioId == null || espacioId.isEmpty()) {
            throw new InvalidEventDataException("ID de espacio no puede ser nulo o vacío");
        }

        EspacioFisico espacio = espacioService.obtenerEspacioPorId(espacioId)
                .orElseThrow(() -> new EntidadNoEncontradaException(espacioId));

        if (espacio.getEstado() != EspacioFisico.EstadoEspacio.ACTIVO) {
            throw new EspacioNoDisponibleException("Espacio no está activo");
        }

        if (plazas <= 0) {
            throw new InvalidEventDataException("Las plazas deben ser mayores a cero");
        }

        if (plazas > espacio.getCapacidad()) {
            throw new InvalidEventDataException("Plazas exceden capacidad del espacio");
        }

        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            throw new InvalidEventDataException("Fechas inválidas");
        }

        if (!verificarDisponibilidadUseCase.verificarDisponibilidad(espacioId, fechaInicio, fechaFin, plazas)) {
            throw new EspacioNoDisponibleException("Espacio no disponible en esas fechas");
        }

        return espacio;
    }
}