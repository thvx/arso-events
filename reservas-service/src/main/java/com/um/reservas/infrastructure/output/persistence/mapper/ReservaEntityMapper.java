package com.um.reservas.infrastructure.output.persistence.mapper;

import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.model.Reserva;
import com.um.reservas.infrastructure.output.persistence.entity.EventoDocument;
import com.um.reservas.infrastructure.output.persistence.entity.ReservaDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservaEntityMapper {

    @Mapping(target = "evento", source = "evento", ignore = true)
    Reserva toDomain(ReservaDocument entity);

    @Mapping(target = "evento", source = "evento", ignore = true)
    ReservaDocument toEntity(Reserva domain);

    default Reserva toDomainWithEvento(ReservaDocument entity, Evento evento) {
        Reserva reserva = toDomain(entity);
        reserva.setEvento(evento);
        return reserva;
    }

    default ReservaDocument toEntityWithEvento(Reserva domain, EventoDocument eventoDocument) {
        ReservaDocument document = toEntity(domain);
        document.setEvento(eventoDocument);
        return document;
    }
}