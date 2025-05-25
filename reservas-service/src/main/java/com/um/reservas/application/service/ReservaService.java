package com.um.reservas.application.service;

import com.um.reservas.application.exception.EventoNoEncontradoException;
import com.um.reservas.application.exception.ReservaNoAceptadaException;
import com.um.reservas.application.exception.ReservaNoEncontradaException;
import com.um.reservas.application.ports.input.ReservaServicePort;
import com.um.reservas.application.ports.output.EventPublisherPort;
import com.um.reservas.application.ports.output.EventoRepositoryPort;
import com.um.reservas.application.ports.output.ReservaRepositoryPort;
import com.um.reservas.domain.event.ReservaCanceladaEvent;
import com.um.reservas.domain.event.ReservaCreadaEvent;
import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.model.Reserva;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService implements ReservaServicePort {

    private final EventoRepositoryPort eventoRepository;
    private final ReservaRepositoryPort reservaRepository;
    private final EventPublisherPort eventPublisherPort;

    public ReservaService(@Qualifier("eventoPersistenceAdapter") EventoRepositoryPort eventoRepository,
                          ReservaRepositoryPort reservaRepository,
                          EventPublisherPort eventPublisherPort) {
        this.eventoRepository = eventoRepository;
        this.reservaRepository = reservaRepository;
        this.eventPublisherPort = eventPublisherPort;
    }

    @Override
    @Transactional
    public String realizarReserva(String eventoId, String usuarioId, int plazas) {
        if (plazas <= 0) {
            throw new ReservaNoAceptadaException("El número de plazas debe ser positivo");
        }

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new EventoNoEncontradoException(eventoId));

        if (evento.isCancelado()) {
            throw new ReservaNoAceptadaException("No se puede reservar en un evento cancelado");
        }

        if (evento.getPlazasDisponibles() < plazas) {
            throw new ReservaNoAceptadaException("No hay suficientes plazas disponibles");
        }

        Reserva reserva = new Reserva();
        reserva.setIdUsuario(usuarioId);
        reserva.setPlazasReservadas(plazas);
        reserva.setCancelada(false);
        reserva.setEvento(evento);

        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Publicar evento
        ReservaCreadaEvent eventoReservaCreada = new ReservaCreadaEvent(
                reservaGuardada.getId(),
                eventoId,
                usuarioId,
                plazas
        );
        eventPublisherPort.publishEvent("reserva-creada", eventoReservaCreada);

        return reservaGuardada.getId();
    }

    @Transactional
    @Override
    public void cancelarReserva(String reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ReservaNoEncontradaException(reservaId));

        if (reserva.isCancelada()) {
            throw new ReservaNoAceptadaException("La reserva ya está cancelada");
        }

        reserva.setCancelada(true);
        reservaRepository.save(reserva);

        // Publicar evento
        ReservaCanceladaEvent eventoReservaCancelada = new ReservaCanceladaEvent(
                reservaId,
                reserva.getEvento().getId(),
                reserva.getPlazasReservadas()
        );
        eventPublisherPort.publishEvent("reserva-cancelada", eventoReservaCancelada);
    }

    @Override
    public Reserva obtenerReserva(String id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaNoEncontradaException(id));
    }

    @Override
    public List<Reserva> obtenerReservasPorEvento(String eventoId) {
        if (eventoRepository.findById(eventoId).isEmpty()) {
            throw new EventoNoEncontradoException(eventoId);
        }
        return reservaRepository.findByEventoId(eventoId);
    }
}