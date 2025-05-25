package com.um.eventos.infrastructure.adapters.input.rest.dto.response;

import com.um.eventos.domain.model.CategoriaEvento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResumenEventoDTO {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private CategoriaEvento categoria;
    private String espacioNombre;
    private String espacioDireccion;
    private List<PuntoInteresDTO> puntosInteres;

    @Getter
    public static class PuntoInteresDTO {
        private String nombre;
        private double distancia;
    }
}