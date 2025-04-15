package com.um.espacios.infrastructure.adapters.input.rest.mapper;

import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.domain.model.PuntoDeInteres;
import com.um.espacios.domain.model.Ubicacion;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.AsignarPuntosRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.CrearEspacioRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioResponse;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.PuntoDeInteresResponse;
import com.um.espacios.domain.model.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.*;
import com.um.espacios.domain.model.EstadoEspacio;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        imports = {EstadoEspacio.class})
public interface EspacioDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", expression = "java(EstadoEspacio.ACTIVO)")
    @Mapping(target = "puntosDeInteres", ignore = true)
    EspacioFisico toDomain(CrearEspacioRequest request);

    @Mapping(target = "ubicacion", source = "ubicacion")
    EspacioFisico toDomainWithUbicacion(CrearEspacioRequest request);

    EspacioResponse toResponse(EspacioFisico espacio);

    List<EspacioResponse> toResponseList(List<EspacioFisico> espacios);

    @Mapping(target = "longitud", source = "longitud")
    @Mapping(target = "latitud", source = "latitud")
    @Mapping(target = "direccion", source = "direccion")
    Ubicacion toUbicacionDomain(CrearEspacioRequest.UbicacionRequest ubicacion);

    List<PuntoDeInteres> toPuntosDomain(List<AsignarPuntosRequest.PuntoDeInteresRequest> puntos);

    List<PuntoDeInteresResponse> toPuntosResponseList(List<PuntoDeInteres> puntos);
}