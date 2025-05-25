package com.um.reservas.infrastructure.adapters.input.rabbit;

import com.um.reservas.application.ports.input.EventConsumerPort;
import com.um.reservas.application.ports.output.ReservaRepositoryPort;
import com.um.reservas.domain.model.Reserva;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventConsumerAdapter implements EventConsumerPort {

    private final ReservaRepositoryPort reservaRepository;

    @Override
    public void handleEventoCancelado(String eventoId) {
        log.info("Recibido evento de cancelación para evento ID: {}", eventoId);

        // Obtener todas las reservas activas para este evento
        List<Reserva> reservasActivas = reservaRepository.findByEventoId(eventoId);

        // Cancelar cada reserva
        reservasActivas.forEach(reserva -> {
            reserva.setCancelada(true);
            reservaRepository.save(reserva);
            log.info("Reserva ID: {} cancelada debido a cancelación del evento", reserva.getId());
        });
    }

    @Override
    public void handleEventoCreado(String evento) {
        log.info("Recibido evento de creación para evento ID: {}", evento);
    }
}