package com.um.espacios.infrastructure.adapters.output.persistence.repository;

import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.EstadoEspacio;
import com.um.espacios.domain.model.Ocupacion;
import com.um.espacios.domain.model.PuntoDeInteres;
import com.um.espacios.infrastructure.adapters.output.persistence.mapper.EspacioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MongoEspacioRepository implements EspacioRepository {

    private final SpringDataEspacioRepository springDataEspacioRepository;

    @Override
    public EspacioFisico guardar(EspacioFisico espacio) {
        springDataEspacioRepository.save(EspacioEntityMapper.toDocument(espacio));
        return espacio;
    }

    @Override
    public void actualizar(EspacioFisico espacio) {
        springDataEspacioRepository.save(EspacioEntityMapper.toDocument(espacio));
    }

    @Override
    public Optional<EspacioFisico> buscarPorId(String id) {
        return springDataEspacioRepository.findById(id)
                .map(EspacioEntityMapper::toModel);
    }

    @Override
    public List<Ocupacion> ocupacionesActivas(String idEspacio) {
        return Collections.emptyList();
    }

    @Override
    public List<PuntoDeInteres> buscarPuntosDeInteresCercanos(double latitud, double longitud) {
        return List.of();
    }

    @Override
    public List<PuntoDeInteres> crearPuntosDeInteres(List<PuntoDeInteres> puntosDeInteres) {
        return List.of();
    }

    @Override
    public List<EspacioFisico> buscarDisponibles(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima) {
        return springDataEspacioRepository.findAll().stream()
                .map(EspacioEntityMapper::toModel)
                .filter(e -> e.getCapacidad() >= capacidadMinima && e.getEstado().equals(EstadoEspacio.ACTIVO))
                .toList();
    }

}