package com.um.espacios.infrastructure.adapters.input.rest.controller;

import com.um.espacios.application.ports.input.*;
import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import com.um.espacios.domain.model.EspacioFisico;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioDisponibleResponse;
import com.um.espacios.infrastructure.adapters.input.rest.dto.response.EspacioResponse;
import com.um.espacios.infrastructure.adapters.input.rest.dto.request.*;
import com.um.espacios.infrastructure.adapters.input.rest.mapper.EspacioDtoMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioFisicoController {

    private final CrearEspacioUseCase crearEspacioUseCase;
    private final ModificarEspacioUseCase modificarEspacioUseCase;
    private final CambiarEstadoUseCase cambiarEstadoUseCase;
    private final BuscarEspaciosDisponiblesUseCase buscarEspaciosDisponiblesUseCase;
    private final AsignarPuntosDeInteresUseCase asignarPuntosUseCase;
    @Qualifier("espacioDtoMapper")
    private final EspacioDtoMapper mapper;

    @PostMapping
    public ResponseEntity<EspacioResponse> crearEspacio(
            @RequestBody @Valid CrearEspacioRequest request) {

        EspacioFisico espacio = mapper.toDomain(request);
        espacio = crearEspacioUseCase.crear(espacio);

        EspacioResponse response = mapper.toResponse(espacio);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}/puntos-interes")
    public ResponseEntity<Void> asignarPuntosDeInteres(
            @PathVariable String id,
            @RequestBody @Valid AsignarPuntosRequest request) {

        asignarPuntosUseCase.asignar(id, mapper.toPuntosDomain(request.getPuntos()));
        return ResponseEntity.ok().build();
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

    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarEspacio(@PathVariable String id) {
        cambiarEstadoUseCase.activar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<EspacioDisponibleResponse>> buscarEspaciosDisponibles(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin,
            @RequestParam int capacidadMin) {

        List<EspacioFisico> espacios = buscarEspaciosDisponiblesUseCase
                .buscar(fechaInicio, fechaFin, capacidadMin);

        return ResponseEntity.ok(mapper.toDisponiblesResponse(espacios));
    }
}