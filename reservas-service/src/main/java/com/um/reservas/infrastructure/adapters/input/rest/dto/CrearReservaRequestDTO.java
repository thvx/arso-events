package com.um.reservas.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearReservaRequestDTO {
    @NotBlank(message = "El ID de evento no puede estar vacío")
    private String eventoId;

    @NotBlank(message = "El ID de usuario no puede estar vacío")
    private String usuarioId;

    @NotNull(message = "El número de plazas no puede ser nulo")
    @Min(value = 1, message = "El número de plazas debe ser al menos 1")
    private Integer plazas;
}