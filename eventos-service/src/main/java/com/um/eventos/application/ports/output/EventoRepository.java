package com.um.eventos.application.ports.output;

import com.um.eventos.domain.model.Evento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    Evento guardar(Evento evento);

    Optional<Evento> buscarPorId(String id);

    List<Evento> buscarPorFecha(String mes, String anio);

    List<Evento> buscarEventosEnEspacioEntreFechas(String espacioId, LocalDateTime fechaInicio,
                                                   LocalDateTime fechaFin, boolean soloActivos);

    List<Evento> buscarEventosEnEspacio(String espacioId, boolean soloActivos);

    List<Evento> listarEventosActivos();

    void eliminar(String id);

    void actualizar(String id, Evento evento);
}