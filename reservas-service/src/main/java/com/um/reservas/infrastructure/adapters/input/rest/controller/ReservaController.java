package com.um.reservas.infrastructure.adapters.input.rest.controller;

import com.um.reservas.application.ports.input.ReservaServicePort;
import com.um.reservas.infrastructure.adapters.input.rest.dto.*;
import com.um.reservas.infrastructure.adapters.input.rest.mapper.EventoDtoMapper;
import com.um.reservas.infrastructure.adapters.input.rest.mapper.ReservaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaServicePort reservaService;
    private final ReservaDtoMapper reservaDtoMapper;
    private final EventoDtoMapper eventoDtoMapper;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(
            @Valid @RequestBody CrearReservaRequestDTO request) {

        String reservaId = reservaService.realizarReserva(
                request.getEventoId(),
                request.getUsuarioId(),
                request.getPlazas());

        ReservaResponseDTO response = reservaDtoMapper.toDto(
                reservaService.obtenerReserva(reservaId));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable String id) {
        return ResponseEntity.ok(
                reservaDtoMapper.toDto(reservaService.obtenerReserva(id)));
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorEvento(
            @PathVariable String eventoId) {

        List<ReservaResponseDTO> response = reservaService.obtenerReservasPorEvento(eventoId)
                .stream()
                .map(reservaDtoMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable String id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/evento/{eventoId}/detalle")
    public ResponseEntity<EventoResponseDTO> obtenerEventoConReservas(
            @PathVariable String eventoId) {

        var reservas = reservaService.obtenerReservasPorEvento(eventoId);
        if (reservas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EventoResponseDTO response = eventoDtoMapper.toDto(reservas.get(0).getEvento());
        return ResponseEntity.ok(response);
    }
}