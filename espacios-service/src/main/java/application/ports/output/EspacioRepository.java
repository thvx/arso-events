package application.ports.output;

import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.EspacioFisico;
import domain.model.Ocupacion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EspacioRepository {
    EspacioFisico crear(EspacioFisico espacio);
    Optional<EspacioFisico> buscarPorId(String id) throws EspacioConOcupacionesActivasException;
    void actualizar(EspacioFisico espacio);
    List<Ocupacion> ocupacionesActivas(String idEspacio);
    boolean tieneOcupacionesActivas(String idEspacio);
    List<EspacioFisico> buscarDisponibles(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima);
}