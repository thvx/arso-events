package com.um.espacios.application.ports.input;

import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;

public interface EliminarEspacioUseCase {
    void eliminarEspacio(String espacioId) throws EspacioConOcupacionesActivasException;
}