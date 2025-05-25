package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.AsignarPuntosDeInteresUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.application.ports.output.EventPublisherPort;
import com.um.espacios.domain.events.PuntosInteresAsignadosEvent;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.PuntoDeInteres;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsignarPuntosDeInteresUseCaseImpl implements AsignarPuntosDeInteresUseCase {
    private final EspacioRepository espacioRepository;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void asignar(String espacioId, List<PuntoDeInteres> puntos) {
        EspacioFisico espacio = espacioRepository.buscarPorId(espacioId)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacioRepository.crearPuntosDeInteres(puntos);
        espacioRepository.actualizar(espacio);
        eventPublisherPort.publicarPuntosInteresAsignados(new PuntosInteresAsignadosEvent(
                espacioId,
                puntos.stream().map(PuntoDeInteres::getNombre).collect(Collectors.toList()),
                Instant.now()
        ));
    }
}