package com.um.espacios.infrastructure.adapters.input.rest.controller;

import com.um.espacios.application.ports.input.*;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioCreadoResponse;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.*;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioResponse;
import com.um.espacios.infrastructure.adapters.input.rest.mapper.EspacioDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioFisicoController {

    private final CrearEspacioUseCase crearEspacioUseCase;
    private final ModificarEspacioUseCase modificarEspacioUseCase;
    private final CambiarEstadoUseCase cambiarEstadoUseCase;
    private final ObtenerEspacioUseCase obtenerEspacioUseCase;
    private final EliminarEspacioUseCase eliminarEspacioUseCase;

    @Qualifier("espacioDtoMapper")
    private final EspacioDtoMapper mapper;

    @PostMapping
    public ResponseEntity<EspacioCreadoResponse> crearEspacio(
            @RequestBody @Valid CrearEspacioRequest request) {

        EspacioFisico espacio = mapper.toDomain(request);
        espacio = crearEspacioUseCase.crear(espacio);

        EspacioCreadoResponse response = mapper.toResponse(espacio);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> modificarEspacio(
            @PathVariable String id,
            @RequestBody @Valid ModificarEspacioRequest request) {

        modificarEspacioUseCase.modificar(
                id,
                request.getNombre(),
                request.getCapacidad(),
                request.getDescripcion());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarEspacio(@PathVariable String id) {
        try{
            cambiarEstadoUseCase.activar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarEspacio(@PathVariable String id) {
        try {
            cambiarEstadoUseCase.desactivar(id);
            return ResponseEntity.ok().build();
        } catch (EspacioConOcupacionesActivasException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspacioResponse> obtenerEspacio(@PathVariable String id) {
        try{
            EspacioFisico espacio = obtenerEspacioUseCase.obtenerEspacio(id);
            EspacioResponse response = mapper.toEspacioResponse(espacio);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspacio(@PathVariable String id) {
        try {
            eliminarEspacioUseCase.eliminarEspacio(id);
            return ResponseEntity.noContent().build();
        } catch (EspacioConOcupacionesActivasException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}