package com.um.espacios.infrastructure.adapters.input.rest.controller;

import com.um.espacios.application.ports.input.AsignarPuntosDeInteresUseCase;
import com.um.espacios.application.ports.input.ObtenerPuntosDeInteresUseCase;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.AsignarPuntosRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.PuntoDeInteresResponse;
import com.um.espacios.infrastructure.adapters.input.rest.mapper.EspacioDtoMapper;
import com.um.espacios.infrastructure.adapters.input.rest.mapper.PuntoDeInteresDtoMapper;
import com.um.espacios.application.ports.input.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/espacios/{espacioId}/puntos-interes")
@RequiredArgsConstructor
public class PuntosInteresController {

    private final ObtenerPuntosDeInteresUseCase obtenerPuntosUseCase;
    private final AsignarPuntosDeInteresUseCase asignarPuntosUseCase;
    private final EspacioDtoMapper espacioMapper;
    private final PuntoDeInteresDtoMapper puntoMapper;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PuntoDeInteresResponse>>> obtenerPuntosCercanos(
            @PathVariable String espacioId,
            @RequestParam @Valid double latitud,
            @RequestParam @Valid double longitud) {

        List<PuntoDeInteresResponse> puntos = puntoMapper.toResponseList(
                obtenerPuntosUseCase.obtenerPuntos(latitud, longitud));

        List<EntityModel<PuntoDeInteresResponse>> resources = puntos.stream()
                .map(punto -> EntityModel.of(punto,
                        linkTo(methodOn(this.getClass())
                                .obtenerPuntosCercanos(espacioId, latitud, longitud)).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(resources,
                linkTo(methodOn(this.getClass())
                        .obtenerPuntosCercanos(espacioId, latitud, longitud)).withSelfRel(),
                linkTo(methodOn(EspacioFisicoController.class)
                        .buscarEspaciosDisponibles(null)).withRel("available-spaces")));
    }

    @PostMapping
    public ResponseEntity<Void> asignarPuntos(
            @PathVariable String espacioId,
            @RequestBody @Valid AsignarPuntosRequest request) {

        asignarPuntosUseCase.asignar(espacioId, espacioMapper.toPuntosDomain(request.getPuntos()));
        return ResponseEntity.noContent().build();
    }
}