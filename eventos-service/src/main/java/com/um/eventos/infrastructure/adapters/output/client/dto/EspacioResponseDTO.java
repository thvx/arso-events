package com.um.eventos.infrastructure.adapters.output.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EspacioResponseDTO {
    private String id;
    private String nombre;
    private int capacidad;
    private String estado;
}