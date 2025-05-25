package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.CrearEspacioUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.application.ports.output.EventPublisherPort;
import com.um.espacios.domain.events.EspacioCreadoEvent;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
class CrearEspacioUseCaseImpl implements CrearEspacioUseCase {
    private final EspacioRepository espacioRepository;
    private final EventPublisherPort eventPublisher;

    @Override
    public EspacioFisico crear(EspacioFisico espacio) {
        EspacioFisico creado = espacioRepository.guardar(espacio);

        eventPublisher.publicarEspacioCreado(new EspacioCreadoEvent(
                creado.getId(),
                creado.getNombre(),
                Instant.now()
        ));

        return creado;
    }
}