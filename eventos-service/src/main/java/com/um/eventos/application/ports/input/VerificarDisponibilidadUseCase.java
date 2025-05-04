package com.um.eventos.application.ports.input;

import java.time.LocalDateTime;

public interface VerificarDisponibilidadUseCase {
    boolean verificarDisponibilidad(String espacioId, LocalDateTime fechaInicio, LocalDateTime fechaFin, int plazas);
}