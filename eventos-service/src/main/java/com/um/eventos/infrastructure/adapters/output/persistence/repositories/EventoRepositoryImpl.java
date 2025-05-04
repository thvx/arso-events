package com.um.eventos.infrastructure.adapters.output.persistence.repositories;

import com.um.eventos.domain.model.Evento;
import com.um.eventos.infrastructure.adapters.output.persistence.entities.EventoEntity;
import com.um.eventos.infrastructure.adapters.output.persistence.mapper.EventoPersistenceMapper;
import com.um.eventos.application.ports.output.EventoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EventoRepositoryImpl implements EventoRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private EventoPersistenceMapper mapper;

    @Override
    @Transactional
    public Evento guardar(Evento evento) {
        EventoEntity entity = mapper.toEntity(evento);
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            entity = em.merge(entity);
        }
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Evento> buscarPorId(String id) {
        EventoEntity entity = em.find(EventoEntity.class, id);
        return Optional.ofNullable(entity)
                .map(mapper::toDomain);
    }

    @Override
    public List<Evento> buscarPorFecha(String mes, String anio) {
        int mesInt = Integer.parseInt(mes);
        int anioInt = Integer.parseInt(anio);

        TypedQuery<EventoEntity> query = em.createQuery(
                "SELECT e FROM EventoEntity e WHERE MONTH(e.ocupacion.fechaInicio) = :mes " +
                        "AND YEAR(e.ocupacion.fechaInicio) = :anio AND e.cancelado = false",
                EventoEntity.class);

        query.setParameter("mes", mesInt);
        query.setParameter("anio", anioInt);

        return query.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacioEntreFechas(String espacioId, LocalDateTime fechaInicio,
                                                          LocalDateTime fechaFin, boolean soloActivos) {
        TypedQuery<EventoEntity> query = em.createQuery(
                "SELECT e FROM EventoEntity e " +
                        "WHERE e.ocupacion.espacioId = :espacioId " +
                        "AND ((e.ocupacion.fechaInicio BETWEEN :fechaInicio AND :fechaFin) " +
                        "OR (e.ocupacion.fechaFin BETWEEN :fechaInicio AND :fechaFin) " +
                        "OR (:fechaInicio BETWEEN e.ocupacion.fechaInicio AND e.ocupacion.fechaFin)) " +
                        "AND (:soloActivos = false OR e.cancelado = false)",
                EventoEntity.class);

        query.setParameter("espacioId", espacioId);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);
        query.setParameter("soloActivos", soloActivos);

        return query.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacio(String espacioId, boolean soloActivos) {
        TypedQuery<EventoEntity> query = em.createQuery(
                "SELECT e FROM EventoEntity e " +
                        "WHERE e.ocupacion.espacioId = :espacioId " +
                        "AND (:soloActivos = false OR e.cancelado = false)",
                EventoEntity.class);

        query.setParameter("espacioId", espacioId);
        query.setParameter("soloActivos", soloActivos);

        return query.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Evento> listarEventosActivos() {
        TypedQuery<EventoEntity> query = em.createQuery(
                "SELECT e FROM EventoEntity e WHERE e.cancelado = false",
                EventoEntity.class);

        return query.getResultList().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(String id) {
        EventoEntity entity = em.find(EventoEntity.class, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    @Override
    @Transactional
    public void actualizar(String id, Evento evento) {
        EventoEntity existingEntity = em.find(EventoEntity.class, id);
        if (existingEntity != null) {
            EventoEntity updatedEntity = mapper.toEntity(evento);
            updatedEntity.setId(id);
            em.merge(updatedEntity);
        }
    }
}