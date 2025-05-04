package com.um.eventos.application.service;

import com.um.eventos.application.ports.input.CrearEventoUseCase;
import com.um.eventos.application.ports.input.VerificarDisponibilidadUseCase;
import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.application.ports.output.EspacioServicePort;
import com.um.eventos.domain.exceptions.*;
import com.um.eventos.domain.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.Optional;


@ApplicationScoped
public class CrearEventoUseCaseImpl implements CrearEventoUseCase {
    private final EventoRepository eventoRepository;
    private final EspacioServicePort espacioService;
    private final VerificarDisponibilidadUseCase verificarDisponibilidadUseCase;

    @Inject
    public CrearEventoUseCaseImpl(EventoRepository eventoRepository, EspacioServicePort espacioService) {
        this.eventoRepository = eventoRepository;
        this.espacioService = espacioService;
        this.verificarDisponibilidadUseCase = new VerificarDisponibilidadUseCaseImpl(eventoRepository);
    }

    @Override
    public Evento crearEvento(String nombre, String descripcion, String organizador,
                              int plazas, CategoriaEvento categoria,
                              LocalDateTime fechaInicio, LocalDateTime fechaFin,
                              String espacioId) throws EspacioNoDisponibleException, EntidadNoEncontradaException {

        validarFechas(fechaInicio, fechaFin);

        Optional<EspacioFisico> espacio = obtenerYValidarEspacio(espacioId, fechaInicio, fechaFin, plazas);

        if (espacio.isEmpty()) {
            throw new EntidadNoEncontradaException("Espacio no disponible o no encontrado");
        }

        Ocupacion ocupacion = crearOcupacion(fechaInicio, fechaFin, espacio.get());

        Evento evento = Evento.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .organizador(organizador)
                .plazas(plazas)
                .cancelado(false)
                .categoria(categoria)
                .ocupacion(ocupacion)
                .build();

        return eventoRepository.guardar(evento);
    }

    private Optional<EspacioFisico> obtenerYValidarEspacio(String espacioId,
                                                           LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                                           int plazas) throws EntidadNoEncontradaException, EspacioNoDisponibleException {
        if(verificarDisponibilidadUseCase.verificarDisponibilidad(espacioId, fechaInicio, fechaFin, plazas)){
            throw new EspacioNoDisponibleException("El espacio no está disponible en las fechas solicitadas");
        }else{
            return espacioService.obtenerEspacioPorId(espacioId);
        }
    }

    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio.isBefore(LocalDateTime.now())) {
            throw new InvalidEventDataException("Fecha de inicio no puede ser en el pasado");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new InvalidEventDataException("Fecha de fin no puede ser antes de la de inicio");
        }
    }

    private Ocupacion crearOcupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacio) {
        return Ocupacion.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .espacio(espacio)
                .build();
    }
}