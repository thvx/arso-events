package infrastructure.persistence.repository;

import application.ports.output.EspacioRepository;
import domain.exceptions.EspacioConOcupacionesActivasException;
import domain.model.EspacioFisico;
import domain.model.Ocupacion;
import infrastructure.persistence.entities.EspacioFisicoEntity;
import infrastructure.persistence.mapper.EspacioMapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class JPAEspacioRepository implements EspacioRepository {

    // Simulación de persistencia en memoria
    private final Map<String, EspacioFisicoEntity> fakeDb = new HashMap<>();
    private final Map<String, List<Ocupacion>> ocupacionesDb = new HashMap<>();

    @Override
    public EspacioFisico crear(EspacioFisico espacio) {
        String idGenerado = UUID.randomUUID().toString();
        espacio.setId(idGenerado);

        EspacioFisicoEntity entity = EspacioMapper.toEntity(espacio);
        fakeDb.put(idGenerado, entity);
        ocupacionesDb.put(idGenerado, new ArrayList<>()); // Inicializar lista vacía de ocupaciones

        return espacio;
    }

    @Override
    public void guardar(EspacioFisico espacio) {
        // Simula persistencia de cambios
        fakeDb.put(espacio.getId(), EspacioMapper.toEntity(espacio));
    }

    @Override
    public void actualizar(EspacioFisico espacio) {
        // Equivalente a guardar en este mock
        guardar(espacio);
    }

    @Override
    public Optional<EspacioFisico> buscarPorId(String id) throws EspacioConOcupacionesActivasException {
        EspacioFisicoEntity entity = fakeDb.get(id);
        return entity == null ? Optional.empty() : Optional.of(EspacioMapper.toModel(entity));
    }

    @Override
    public List<Ocupacion> ocupacionesActivas(String idEspacio) {
        LocalDateTime ahora = LocalDateTime.now();
        return ocupacionesDb.getOrDefault(idEspacio, Collections.emptyList())
                .stream()
                .filter(Ocupacion::estaActiva)
                .filter(o -> o.getFechaFin().isAfter(ahora))
                .collect(Collectors.toList());
    }

    @Override
    public boolean tieneOcupacionesActivas(String id) {
        return !ocupacionesActivas(id).isEmpty();
    }

    @Override
    public List<EspacioFisico> buscarDisponibles(LocalDateTime inicio, LocalDateTime fin, int capacidadMinima) {
        return fakeDb.values().stream()
                .map(EspacioMapper::toModel)
                .filter(espacio -> espacio.getCapacidad() >= capacidadMinima)
                .filter(espacio -> espacio.estaDisponible(inicio, fin, ocupacionesActivas(espacio.getId())))
                .collect(Collectors.toList());
    }
}
