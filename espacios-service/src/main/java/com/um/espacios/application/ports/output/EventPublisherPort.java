package com.um.espacios.application.ports.output;

import com.um.espacios.domain.events.*;

// application/ports/output/EventPublisherPort.java
public interface EventPublisherPort {
    void publicarEspacioCreado(EspacioCreadoEvent event);
    void publicarEspacioActivado(EspacioActivadoEvent event);
    void publicarEspacioDesactivado(EspacioDesactivadoEvent event);
    void publicarPuntosInteresAsignados(PuntosInteresAsignadosEvent event);
}