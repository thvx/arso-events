package com.um.espacios.application.ports.output;

import com.um.espacios.domain.exceptions.ServicioNoDisponibleException;

public interface EventoServicePort {
    boolean tieneOcupacionesActivas(String idEspacio) throws ServicioNoDisponibleException;
    // true si tiene ocupaciones activas
    // false si no tiene ocupaciones activas

    // eventos pasa una lista de eventos por espacio. Si no hay eventos, devuelve una lista vacia
}