package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.CambiarEstadoUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.Ocupacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CambiarEstadoEspacioUseCaseImpl implements CambiarEstadoUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public void desactivar(String idEspacio) throws EspacioConOcupacionesActivasException {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        List<Ocupacion> ocupaciones = espacioRepository.ocupacionesActivas(idEspacio);
        espacio.desactivarEspacio(ocupaciones);
        espacioRepository.actualizar(espacio);
    }

    @Override
    public void activar(String idEspacio) {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacio.activar();
        espacioRepository.actualizar(espacio);
    }
}