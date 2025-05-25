package com.um.eventos.application.ports.output;

import com.um.eventos.domain.model.EspacioFisico;

import java.util.Optional;

public interface EspacioServicePort{
    Optional<EspacioFisico> obtenerEspacio(String espacioId);
    boolean verificarDisponibilidadEspacio(String espacioId);
}