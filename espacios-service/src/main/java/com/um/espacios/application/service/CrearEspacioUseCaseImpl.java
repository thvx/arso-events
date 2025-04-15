package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.CrearEspacioUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CrearEspacioUseCaseImpl implements CrearEspacioUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public EspacioFisico crear(EspacioFisico espacio) {
        return espacioRepository.guardar(espacio);
    }
}