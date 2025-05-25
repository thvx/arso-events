package com.um.reservas.infrastructure.adapters.input.rest.mapper;

import com.um.reservas.domain.model.Evento;
import com.um.reservas.domain.model.Reserva;
import com.um.reservas.infrastructure.adapters.input.rest.dto.EventoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EventoDtoMapper {

    @Mapping(target = "reservasIds", source = "reservas", qualifiedByName = "mapReservasToIds")
    EventoResponseDTO toDto(Evento domain);

    @Mapping(target = "reservas", ignore = true)
    Evento toDomain(EventoResponseDTO dto);

    @Named("mapReservasToIds")
    default List<String> mapReservasToIds(List<Reserva> reservas) {
        if (reservas == null) return null;
        return reservas.stream()
                .map(Reserva::getId)
                .collect(Collectors.toList());
    }
}