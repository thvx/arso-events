package com.um.espacios.application.ports.output;

import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.Ocupacion;
import com.um.espacios.domain.model.PuntoDeInteres;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EspacioRepository {
    EspacioFisico guardar(EspacioFisico espacio);
    void actualizar(EspacioFisico espacio);
    Optional<EspacioFisico> buscarPorId(String id);
    List<EspacioFisico> buscarDisponibles(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima);
    List<Ocupacion> ocupacionesActivas(String idEspacio);
    List<PuntoDeInteres> buscarPuntosDeInteresCercanos(double latitud, double longitud);
    List<PuntoDeInteres> crearPuntosDeInteres(List<PuntoDeInteres> puntosDeInteres);
}