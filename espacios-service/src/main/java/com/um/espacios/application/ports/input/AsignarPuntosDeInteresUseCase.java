package com.um.espacios.application.ports.input;

import com.um.espacios.domain.model.PuntoDeInteres;
import java.util.List;

public interface AsignarPuntosDeInteresUseCase {
    void asignar(String espacioId, List<PuntoDeInteres> puntos);
}