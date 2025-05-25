package com.um.reservas.infrastructure.adapters.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoResponseDTO {
    private String id;
    private int plazasDisponibles;
    private boolean cancelado;
    private List<String> reservasIds;
}