package com.um.eventos.infrastructure.adapters.output.persistence;

import com.um.eventos.application.ports.output.EventoRepository;
import com.um.eventos.domain.model.Evento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class EventoRepositoryImpl implements EventoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Evento guardar(Evento evento) {
        entityManager.persist(evento);
        return evento;
    }

    @Override
    public Optional<Evento> buscarPorId(String id) {
        return Optional.ofNullable(entityManager.find(Evento.class, id));
    }

    @Override
    public List<Evento> buscarPorFecha(String mes, String anio) {
        String jpql = "SELECT e FROM Evento e WHERE MONTH(e.fechaInicio) = :mes AND YEAR(e.fechaInicio) = :anio";
        TypedQuery<Evento> query = entityManager.createQuery(jpql, Evento.class)
                .setParameter("mes", Integer.parseInt(mes))
                .setParameter("anio", Integer.parseInt(anio));
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacioEntreFechas(String espacioId, LocalDateTime fechaInicio,
                                                          LocalDateTime fechaFin, boolean soloActivos) {
        String jpql = "SELECT e FROM Evento e WHERE e.espacioFisico.id = :espacioId " +
                "AND ((e.fechaInicio BETWEEN :fechaInicio AND :fechaFin) " +
                "OR (e.fechaFin BETWEEN :fechaInicio AND :fechaFin)) " +
                (soloActivos ? "AND e.activo = true" : "");

        return entityManager.createQuery(jpql, Evento.class)
                .setParameter("espacioId", espacioId)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacio(String espacioId, boolean soloActivos) {
        String jpql = "SELECT e FROM Evento e WHERE e.espacioFisico.id = :espacioId " +
                (soloActivos ? "AND e.activo = true" : "");

        return entityManager.createQuery(jpql, Evento.class)
                .setParameter("espacioId", espacioId)
                .getResultList();
    }

    @Override
    public List<Evento> listarTodos() {
        return entityManager.createQuery("SELECT e FROM Evento e", Evento.class)
                .getResultList();
    }

    @Override
    public List<Evento> listarEventosActivos() {
        return entityManager.createQuery("SELECT e FROM Evento e WHERE e.activo = true", Evento.class)
                .getResultList();
    }

    @Override
    public void eliminar(String id) {
        buscarPorId(id).ifPresent(entityManager::remove);
    }

    @Override
    public void actualizar(String id, Evento evento) {
        buscarPorId(id).ifPresent(existing -> {
            evento.setId(id); // Asegurar que