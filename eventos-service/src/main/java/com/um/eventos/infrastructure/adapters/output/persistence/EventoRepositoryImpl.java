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
        if (evento.getOcupacion() != null) {
            // Persistir la ocupación primero si es nueva
            if (evento.getOcupacion().getId() == null) {
                entityManager.persist(evento.getOcupacion());
            }
        }
        entityManager.persist(evento);
        return evento;
    }

    @Override
    public Optional<Evento> buscarPorId(String id) {
        Evento evento = entityManager.find(Evento.class, id);
        if (evento != null) {
            // Forzar carga de relaciones necesarias
            evento.getOcupacion().getEspacio(); // Carga LAZY
        }
        return Optional.ofNullable(evento);
    }

    @Override
    public List<Evento> buscarPorFecha(String mes, String anio) {
        String jpql = "SELECT e FROM Evento e JOIN FETCH e.ocupacion o " +
                "WHERE MONTH(o.fechaInicio) = :mes AND YEAR(o.fechaInicio) = :anio";
        TypedQuery<Evento> query = entityManager.createQuery(jpql, Evento.class)
                .setParameter("mes", Integer.parseInt(mes))
                .setParameter("anio", Integer.parseInt(anio));
        return query.getResultList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacioEntreFechas(String espacioId, LocalDateTime fechaInicio,
                                                          LocalDateTime fechaFin, boolean soloActivos) {
        String jpql = "SELECT e FROM Evento e JOIN FETCH e.ocupacion o " +
                "WHERE o.espacio.id = :espacioId " +
                "AND ((o.fechaInicio BETWEEN :fechaInicio AND :fechaFin) " +
                "OR (o.fechaFin BETWEEN :fechaInicio AND :fechaFin)) " +
                (soloActivos ? "AND e.cancelado = false" : "");

        return entityManager.createQuery(jpql, Evento.class)
                .setParameter("espacioId", espacioId)
                .setParameter("fechaInicio", fechaInicio)
                .setParameter("fechaFin", fechaFin)
                .getResultList();
    }

    @Override
    public List<Evento> buscarEventosEnEspacio(String espacioId, boolean soloActivos) {
        String jpql = "SELECT e FROM Evento e JOIN FETCH e.ocupacion o " +
                "WHERE o.espacio.id = :espacioId " +
                (soloActivos ? "AND e.cancelado = false" : "");

        return entityManager.createQuery(jpql, Evento.class)
                .setParameter("espacioId", espacioId)
                .getResultList();
    }

    @Override
    public List<Evento> listarTodos() {
        return entityManager.createQuery(
                        "SELECT DISTINCT e FROM Evento e LEFT JOIN FETCH e.ocupacion o LEFT JOIN FETCH o.espacio",
                        Evento.class)
                .getResultList();
    }

    @Override
    public List<Evento> listarEventosActivos() {
        return entityManager.createQuery(
                        "SELECT e FROM Evento e JOIN FETCH e.ocupacion WHERE e.cancelado = false",
                        Evento.class)
                .getResultList();
    }

    @Override
    public void eliminar(String id) {
        buscarPorId(id).ifPresent(evento -> {
            if (evento.getOcupacion() != null) {
                entityManager.remove(evento.getOcupacion());
            }
            entityManager.remove(evento);
        });
    }

    @Override
    public void actualizar(String id, Evento evento) {
        buscarPorId(id).ifPresent(existing -> {
            // Actualizar ocupación si existe
            if (evento.getOcupacion() != null) {
                if (existing.getOcupacion() != null) {
                    evento.getOcupacion().setId(existing.getOcupacion().getId());
                }
                entityManager.merge(evento.getOcupacion());
            }

            // Actualizar evento
            evento.setId(id);
            entityManager.merge(evento);
        });
    }
}