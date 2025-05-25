package com.um.espacios.infrastructure.adapters.output.rabbitmq;

import com.um.espacios.application.ports.output.EventPublisherPort;
import com.um.espacios.domain.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitEventPublisherAdapter implements EventPublisherPort {

    private final AmqpTemplate amqpTemplate;

    @Override
    public void publicarEspacioCreado(EspacioCreadoEvent event) {
        publicarEvento("bus.espacios.espacio-creado", event);
    }

    @Override
    public void publicarEspacioActivado(EspacioActivadoEvent event) {
        publicarEvento("bus.espacios.espacio-activado", event);
    }

    @Override
    public void publicarEspacioDesactivado(EspacioDesactivadoEvent event) {
        publicarEvento("bus.espacios.espacio-desactivado", event);
    }

    @Override
    public void publicarPuntosInteresAsignados(PuntosInteresAsignadosEvent event) {
        publicarEvento("bus.espacios.puntos-interes-asignados", event);
    }

    private void publicarEvento(String routingKey, Object event) {
        amqpTemplate.convertAndSend("bus", routingKey, event, message -> {
            message.getMessageProperties().setContentType("application/json");
            return message;
        });
    }
}