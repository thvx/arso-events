package com.um.espacios.application.service;

import com.um.espacios.application.ports.input.BuscarEspaciosDisponiblesUseCase;
import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class BuscarEspaciosDisponiblesUseCaseImpl implements BuscarEspaciosDisponiblesUseCase {
    private final EspacioRepository espacioRepository;

    @Override
    public List<EspacioFisico> buscar(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima) {
        return espacioRepository.buscarDisponibles(inicio, fin, capacidadMinima);
    }
}