package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.ObtenerEspacioUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.exceptions.EntidadNoEncontradaException;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ObtenerEspacioUseCaseImpl implements ObtenerEspacioUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public EspacioFisico obtenerEspacio(String espacioId) throws EntidadNoEncontradaException{
        return espacioRepository.buscarPorId(espacioId)
                .orElseThrow(() -> new EntidadNoEncontradaException("Espacio no encontrado"));
    }
}
