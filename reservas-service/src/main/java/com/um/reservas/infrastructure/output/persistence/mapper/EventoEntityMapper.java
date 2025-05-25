package com.um.reservas.infrastructure.output.persistence.mapper;

import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.model.Reserva;
import com.um.reservas.infrastructure.output.persistence.entity.EventoDocument;
import com.um.reservas.infrastructure.output.persistence.entity.ReservaDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventoEntityMapper {

    @Mapping(target = "reservas", source = "reservas", qualifiedByName = "mapReservas")
    Evento toDomain(EventoDocument entity);

    @Mapping(target = "reservas", source = "reservas", qualifiedByName = "mapReservaDocuments")
    EventoDocument toEntity(Evento domain);

    @Named("mapReservas")
    default List<Reserva> mapReservas(List<ReservaDocument> reservas) {
        if (reservas == null) return null;
        return reservas.stream()
                .map(reserva -> new ReservaEntityMapperImpl().toDomain(reserva))
                .collect(Collectors.toList());
    }

    @Named("mapReservaDocuments")
    default List<ReservaDocument> mapReservaDocuments(List<Reserva> reservas) {
        if (reservas == null) return null;
        return reservas.stream()
                .map(reserva -> new ReservaEntityMapperImpl().toEntity(reserva))
                .collect(Collectors.toList());
    }
}