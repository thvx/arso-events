package com.um.reservas.infrastructure.controller

import com.um.reservas.application.dto.CrearReservaRequestDTO;
import com.um.reservas.application.dto.ReservaResponseDTO;
import com.um.reservas.application.exception.ApplicationException;
import com.um.reservas.application.service.ReservaApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaApplicationService reservaAppService;

    public ReservaController(ReservaApplicationService reservaAppService) {
        this.reservaAppService = reservaAppService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<String> crearReserva(
            @RequestBody CrearReservaRequestDTO command) {
        String reservaId = reservaAppService.crearReserva(command);
        return ResponseEntity.ok(reservaId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('GESTOR_EVENTOS')")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable String id) {
        return ResponseEntity.ok(reservaAppService.obtenerReserva(id));
    }

    @GetMapping("/evento/{eventoId}")
    @PreAuthorize("hasRole('GESTOR_EVENTOS')")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasDeEvento(
            @PathVariable String eventoId) {
        return ResponseEntity.ok(reservaAppService.obtenerReservasDeEvento(eventoId));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}