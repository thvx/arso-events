package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.EliminarEspacioUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.application.ports.output.EventoServicePort;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
class EliminarEspacioUseCaseImpl implements EliminarEspacioUseCase {
    private final EspacioRepository espacioRepository;
    private final EventoServicePort eventoServicePort;

    public void eliminarEspacio(String espacioId) throws EspacioConOcupacionesActivasException, ServicioNoDisponibleException {
        if (eventoServicePort.tieneOcupacionesActivas(espacioId)) {
            throw new EspacioConOcupacionesActivasException("El espacio tiene ocupaciones activas y no se puede eliminar.");
        }
        espacioRepository.eliminar(espacioId);
    }
}