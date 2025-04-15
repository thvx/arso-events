package com.um.espacios.infrastructure.adapters.input.rest.mapper;

import com.um.espacios.domain.model.PuntoDeInteres;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.PuntoDeInteresResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PuntoDeInteresDtoMapper {

    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "distancia", source = "distancia")
    @Mapping(target = "urlWikipedia", source = "urlWikipedia")
    PuntoDeInteresResponse toResponse(PuntoDeInteres punto);

    List<PuntoDeInteresResponse> toResponseList(List<PuntoDeInteres> puntos);
}