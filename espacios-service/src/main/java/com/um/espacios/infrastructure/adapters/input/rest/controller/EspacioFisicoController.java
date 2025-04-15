package com.um.espacios.infrastructure.adapters.input.rest.controller;

import com.um.espacios.application.ports.input.BuscarEspaciosDisponiblesUseCase;
import com.um.espacios.application.ports.input.CambiarEstadoUseCase;
import com.um.espacios.application.ports.input.CrearEspacioUseCase;
import com.um.espacios.application.ports.input.ModificarEspacioUseCase;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.BuscarEspaciosDisponiblesRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.CrearEspacioRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.ModificarEspacioRequest;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioResponse;
import com.um.espacios.application.ports.input.*;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.*;
import com.um.espacios.infrastructure.adapters.input.rest.mapper.EspacioDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioFisicoController {

    private final CrearEspacioUseCase crearEspacioUseCase;
    private final ModificarEspacioUseCase modificarEspacioUseCase;
    private final CambiarEstadoUseCase cambiarEstadoUseCase;
    private final BuscarEspaciosDisponiblesUseCase buscarEspaciosDisponiblesUseCase;
    private final EspacioDtoMapper mapper;

    @PostMapping
    public ResponseEntity<EntityModel<EspacioResponse>> crearEspacio(
            @RequestBody @Valid CrearEspacioRequest request) {

        EspacioFisico espacio = mapper.toDomain(request);
        espacio = crearEspacioUseCase.crear(espacio);

        EspacioResponse response = mapper.toResponse(espacio);

        EntityModel<EspacioResponse> resource = EntityModel.of(response);
        resource.add(linkTo(methodOn(this.getClass()).crearEspacio(request)).withSelfRel());
        resource.add(linkTo(methodOn(this.getClass()).modificarEspacio(espacio.getId(), null)).withRel("modify"));
        resource.add(linkTo(methodOn(PuntosInteresController.class)
                .asignarPuntos(espacio.getId(), null)).withRel("assign-points"));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(resource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modificarEspacio(
            @PathVariable String id,
            @RequestBody @Valid ModificarEspacioRequest request) {

        modificarEspacioUseCase.modificar(
                id,
                request.getNombre(),
                request.getCapacidad(),
                request.getDescripcion());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarEspacio(@PathVariable String id) throws Exception {
        cambiarEstadoUseCase.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarEspacio(@PathVariable String id) {
        cambiarEstadoUseCase.activar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<CollectionModel<EntityModel<EspacioResponse>>> buscarEspaciosDisponibles(
            @RequestBody @Valid BuscarEspaciosDisponiblesRequest request) {

        List<EspacioFisico> espacios = buscarEspaciosDisponiblesUseCase.buscar(
                request.getInicio(),
                request.getFin(),
                request.getCapacidadMinima());

        List<EntityModel<EspacioResponse>> resources = espacios.stream()
                .map(mapper::toResponse)
                .map(response -> EntityModel.of(response,
                        linkTo(methodOn(this.getClass()).buscarEspaciosDisponibles(request)).withSelfRel(),
                        linkTo(methodOn(PuntosInteresController.class)
                                .obtenerPuntosCercanos(response.getId(), 0, 0)).withRel("nearby-points")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(resources,
                linkTo(methodOn(this.getClass()).buscarEspaciosDisponibles(request)).withSelfRel()));
    }
}