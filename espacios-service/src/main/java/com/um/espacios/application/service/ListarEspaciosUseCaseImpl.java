package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.ListarEspaciosUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.exceptions.RepositorioException;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ListarEspaciosUseCaseImpl implements ListarEspaciosUseCase {

    private final EspacioRepository espacioRepository;

    public List<EspacioFisico>listarEspacios() throws RepositorioException{
        try {
            return espacioRepository.listarEspacios();
        } catch (Exception e) {
            throw new RepositorioException("Error al listar los espacios", e);
        }
    }
}