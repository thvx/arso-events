package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.CambiarEstadoUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.application.ports.output.EventosServicePort;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CambiarEstadoEspacioUseCaseImpl implements CambiarEstadoUseCase {
    private final EspacioRepository espacioRepository;
    private final EventosServicePort eventosServicePort;

    @Override
    public void desactivar(String idEspacio) throws Exception {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));

        if(eventosServicePort.tieneOcupacionesActivas(idEspacio)) {
            throw new EspacioConOcupacionesActivasException("El espacio tiene ocupaciones activas");
        }else{
            espacio.desactivar();
            espacioRepository.actualizar(espacio);
        }
    }

    @Override
    public void activar(String idEspacio) throws Exception {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacio.activar();
        espacioRepository.actualizar(espacio);
    }
}