package com.um.espacios.application.ports.input;

import com.um.espacios.domain.model.EspacioFisico;
import java.time.LocalDateTime;
import java.util.List;

public interface BuscarEspaciosDisponiblesUseCase {
    List<EspacioFisico> buscar(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima);
}