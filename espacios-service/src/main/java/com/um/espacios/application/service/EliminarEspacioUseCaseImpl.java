package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.EliminarEspacioUseCase;
import com.um.espacios.application.ports.output.EventosServicePort;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.application.ports.output.EspacioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class EliminarEspacioUseCaseImpl implements EliminarEspacioUseCase {
    private final EspacioRepository espacioRepository;
    private final EventosServicePort eventosServicePort;

    public void eliminarEspacio(String espacioId) throws EspacioConOcupacionesActivasException{
        if (eventosServicePort.tieneOcupacionesActivas(espacioId)) {
            throw new EspacioConOcupacionesActivasException("El espacio tiene ocupaciones activas y no se puede eliminar.");
        }
        espacioRepository.eliminar(espacioId);
    }
}