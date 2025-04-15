package com.um.espacios.infrastructure.adapters.input.rest.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class BuscarEspaciosDisponiblesRequest {
    @NotNull
    private LocalDateTime inicio;

    @NotNull
    private LocalDateTime fin;

    @Positive
    private int capacidadMinima;
}