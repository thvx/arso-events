package com.um.reservas.application.ports.input;

public interface EventConsumerPort {
    void handleEventoCancelado(String eventoId);
    void handleEventoCreado(String evento);
}