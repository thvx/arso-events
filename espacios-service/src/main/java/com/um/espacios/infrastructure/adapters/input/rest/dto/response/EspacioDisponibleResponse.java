package com.um.espacios.infrastructure.adapters.input.rest.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EspacioDisponibleResponse {
    private String id;
    private String nombre;
    private int capacidad;
    private String direccion;
}