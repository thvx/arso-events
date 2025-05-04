package com.um.eventos.infrastructure.adapters.input.rest.dto.request;

import com.um.eventos.domain.model.CategoriaEvento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearEventoRequest {
    @NotBlank(message = "Nombre es requerido")
    private String nombre;

    @NotBlank(message = "Descripción es requerida")
    private String descripcion;

    @NotBlank(message = "Organizador es requerido")
    private String organizador;

    @Positive(message = "Plazas debe ser positivo")
    private int plazas;

    private CategoriaEvento categoria;

    @Future(message = "Fecha inicio debe ser en el futuro")
    private LocalDateTime fechaInicio;

    @Future(message = "Fecha fin debe ser en el futuro")
    private LocalDateTime fechaFin;

    @NotBlank(message = "Espacio ID es requerido")
    private String espacioId;
}