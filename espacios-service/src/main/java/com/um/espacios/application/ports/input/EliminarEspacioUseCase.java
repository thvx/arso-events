package com.um.espacios.application.ports.input;

import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;

public interface EliminarEspacioUseCase {
    void eliminarEspacio(String espacioId) throws EspacioConOcupacionesActivasException, ServicioNoDisponibleException;
}