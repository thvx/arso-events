package com.um.eventos.infrastructure.adapters.input.rest.dto.request;

import com.um.eventos.domain.model.CategoriaEvento;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El organizador es obligatorio")
    @Size(max = 100, message = "El organizador no puede exceder los 100 caracteres")
    private String organizador;

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaEvento categoria;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @Min(value = 1, message = "Debe haber al menos 1 plaza")
    private int plazas;

    @NotBlank(message = "El ID del espacio es obligatorio")
    private String espacioId;

}