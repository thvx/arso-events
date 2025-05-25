package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.CambiarEstadoUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.application.ports.output.EventPublisherPort;
import com.um.espacios.application.ports.output.EventoServicePort;
import com.um.espacios.domain.events.EspacioActivadoEvent;
import com.um.espacios.domain.events.EspacioDesactivadoEvent;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class CambiarEstadoEspacioUseCaseImpl implements CambiarEstadoUseCase {
    private final EspacioRepository espacioRepository;
    private final EventoServicePort eventoServicePort;
    private final EventPublisherPort eventPublisherPort;

    public CambiarEstadoEspacioUseCaseImpl(EspacioRepository espacioRepository,
                                           @Qualifier("RetrofitEventoServiceAdapter") EventoServicePort eventoServicePort,
                                           EventPublisherPort eventPublisherPort) {
        this.espacioRepository = espacioRepository;
        this.eventoServicePort = eventoServicePort;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    public void desactivar(String idEspacio) throws Exception, ServicioNoDisponibleException {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        if(eventoServicePort.tieneOcupacionesActivas(idEspacio)) {
            throw new EspacioConOcupacionesActivasException("El espacio tiene ocupaciones activas");
        }else{
            espacio.desactivar();
            espacioRepository.actualizar(espacio);

            eventPublisherPort.publicarEspacioDesactivado(new EspacioDesactivadoEvent(
                    idEspacio,
                    Instant.now()
            ));
        }
    }

    @Override
    public void activar(String idEspacio) throws Exception {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacio.activar();
        espacioRepository.actualizar(espacio);

        eventPublisherPort.publicarEspacioActivado(new EspacioActivadoEvent(
                idEspacio,
                Instant.now()
        ));
    }
}