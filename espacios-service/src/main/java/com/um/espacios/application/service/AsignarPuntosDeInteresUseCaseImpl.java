package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.AsignarPuntosDeInteresUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.PuntoDeInteres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsignarPuntosDeInteresUseCaseImpl implements AsignarPuntosDeInteresUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public void asignar(String espacioId, List<PuntoDeInteres> puntos) {
        EspacioFisico espacio = espacioRepository.buscarPorId(espacioId)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacioRepository.crearPuntosDeInteres(puntos);
        espacioRepository.actualizar(espacio);
    }
}