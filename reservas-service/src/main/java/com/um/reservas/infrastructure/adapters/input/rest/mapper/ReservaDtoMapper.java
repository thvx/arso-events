package com.um.reservas.infrastructure.adapters.input.rest.mapper;

import com.um.reservas.domain.model.Reserva;
import com.um.reservas.infrastructure.adapters.input.rest.dto.ReservaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservaDtoMapper {

    @Mapping(target = "eventoId", source = "evento.id")
    ReservaResponseDTO toDto(Reserva domain);

    @Mapping(target = "evento", ignore = true)
    Reserva toDomain(ReservaResponseDTO dto);
}