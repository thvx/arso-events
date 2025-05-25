package com.um.eventos.infrastructure.adapters.input.rest.mapper;

import com.um.eventos.domain.model.Evento;
import com.um.eventos.infrastructure.adapters.input.rest.dto.request.EventoRequestDTO;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.EventoResponseDTO;
import com.um.eventos.infrastructure.adapters.input.rest.dto.response.ResumenEventoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface EventoRestMapper {

    @Mapping(target = "ocupacion", ignore = true)
    @Mapping(target = "cancelado", constant = "false")
    Evento toDomain(EventoRequestDTO dto);

    @Mapping(source = "ocupacion.espacio.id", target = "espacioId")
    @Mapping(source = "ocupacion.fechaInicio", target = "fechaInicio")
    @Mapping(source = "ocupacion.fechaFin", target = "fechaFin")
    EventoResponseDTO toResponseDTO(Evento domain);

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "ocupacion.fechaInicio", target = "fechaInicio")
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "ocupacion.espacio.nombre", target = "espacioNombre")
    ResumenEventoDTO toResumenDTO(Evento domain);
}