package com.um.reservas.infrastructure.adapters.input.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.reservas.application.ports.input.EventConsumerPort;
import com.um.reservas.domain.event.EventoCanceladoEvent;
import com.um.reservas.domain.event.EventoCreadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQEventListener {

    private final EventConsumerPort eventConsumerPort;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.evento-cancelado.name}")
    public void handleEventoCancelado(String message) {
        try {
            EventoCanceladoEvent event = objectMapper.readValue(message, EventoCanceladoEvent.class);
            log.info("Evento de cancelación recibido: {}", event);
            eventConsumerPort.handleEventoCancelado(event.getEventoId());
        } catch (Exception e) {
            log.error("Error procesando evento de cancelación", e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.evento-creado.name}")
    public void handleEventoCreado(String message) {
        try {
            EventoCreadoEvent event = objectMapper.readValue(message, EventoCreadoEvent.class);
            log.info("Evento de creación recibido: {}", event);
            eventConsumerPort.handleEventoCreado(event.getEventoId());
        } catch (Exception e) {
            log.error("Error procesando evento de creación", e);
        }
    }
}