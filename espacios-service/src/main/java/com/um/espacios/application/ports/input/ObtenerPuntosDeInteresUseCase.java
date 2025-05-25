package com.um.espacios.application.ports.input;

import com.um.espacios.domain.model.PuntoDeInteres;

import java.util.List;

public interface ObtenerPuntosDeInteresUseCase {
    List<PuntoDeInteres> obtenerPuntos(double latitud, double longitud);
}