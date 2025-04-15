package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.ModificarEspacioUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ModificarEspacioUseCaseImpl implements ModificarEspacioUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public void modificar(String idEspacio, String nombre, int capacidad, String descripcion) {
        EspacioFisico espacio = espacioRepository.buscarPorId(idEspacio)
                .orElseThrow(() -> new IllegalArgumentException("Espacio no encontrado"));
        espacio.modificarDatos(nombre, capacidad, descripcion);
        espacioRepository.actualizar(espacio);
    }
}
