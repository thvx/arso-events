package com.um.espacios.application.ports.input;

import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.exceptions.EntidadNoEncontradaException;

public interface ObtenerEspacioUseCase {
    EspacioFisico obtenerEspacio(String espacioId) throws EntidadNoEncontradaException;
}