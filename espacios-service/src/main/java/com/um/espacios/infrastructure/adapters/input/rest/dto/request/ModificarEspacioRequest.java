package com.um.espacios.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ModificarEspacioRequest {
    @NotBlank
    private String nombre;

    @Positive
    private int capacidad;

    private String descripcion;
}