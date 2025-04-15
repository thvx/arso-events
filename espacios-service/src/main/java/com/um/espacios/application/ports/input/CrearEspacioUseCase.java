package com.um.espacios.application.ports.input;

import com.um.espacios.domain.model.EspacioFisico;

public interface CrearEspacioUseCase {
    EspacioFisico crear(EspacioFisico espacio);
}