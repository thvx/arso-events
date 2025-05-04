package com.um.espacios.application.ports.output;

public interface EventosServicePort{
    boolean tieneOcupacionesActivas(String idEspacio);
    // true si tiene ocupaciones activas
    // false si no tiene ocupaciones activas

    // eventos pasa una lista de eventos por espacio. Si no hay eventos, devuelve una lista vacia
}