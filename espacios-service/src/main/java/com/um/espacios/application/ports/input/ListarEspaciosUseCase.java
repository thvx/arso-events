package com.um.espacios.application.ports.input;

import com.um.espacios.domain.exceptions.RepositorioException;
import com.um.espacios.domain.model.EspacioFisico;

import java.util.List;

public interface ListarEspaciosUseCase {
    List<EspacioFisico>listarEspacios() throws RepositorioException;
}