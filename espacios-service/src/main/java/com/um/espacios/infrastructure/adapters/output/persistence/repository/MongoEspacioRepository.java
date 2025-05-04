package com.um.espacios.infrastructure.adapters.output.persistence.repository;

import com.um.espacios.application.ports.output.EspacioRepository;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.EstadoEspacio;
import com.um.espacios.domain.model.PuntoDeInteres;
import com.um.espacios.infrastructure.adapters.output.persistence.mapper.EspacioEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public void eliminar(String id) {

    }

    @Override
    public Optional<EspacioFisico> buscarPorId(String id) {
        return springDataEspacioRepository.findById(id)
                .map(EspacioEntityMapper::toModel);
    }

    @Override
    public List<EspacioFisico> listarEspacios() {
        return springDataEspacioRepository.findAll()
                .stream()
                .map(EspacioEntityMapper::toModel)
                .toList();
    }

    @Override
    public List<PuntoDeInteres> buscarPuntosDeInteresCercanos(double latitud, double longitud) {
        return List.of();
    }

    @Override
    public void crearPuntosDeInteres(List<PuntoDeInteres> puntosDeInteres) {
    }

}