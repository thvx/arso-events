package com.um.espacios.application.ports.output;

import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.PuntoDeInteres;

import java.util.List;
import java.util.Optional;

public interface EspacioRepository {
    EspacioFisico guardar(EspacioFisico espacio);
    void actualizar(EspacioFisico espacio);
    void eliminar(String id);
    Optional<EspacioFisico> buscarPorId(String id);
    List<EspacioFisico> listarEspacios();


    List<PuntoDeInteres> buscarPuntosDeInteresCercanos(double latitud, double longitud);
    void crearPuntosDeInteres(List<PuntoDeInteres> puntosDeInteres);
}