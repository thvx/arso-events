package com.um.espacios.application.ports.input;

import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;

public interface CambiarEstadoUseCase {
    void activar(String espacioId) throws Exception;
    void desactivar(String espacioId) throws Exception, ServicioNoDisponibleException;
}