package com.um.espacios.application.ports.input;

import com.um.espacios.domain.exceptions.EntidadNoEncontradaException;
import com.um.espacios.domain.model.EspacioFisico;

public interface ObtenerEspacioUseCase {
    EspacioFisico obtenerEspacio(String espacioId) throws EntidadNoEncontradaException;
}