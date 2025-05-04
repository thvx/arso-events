package com.um.eventos.infrastructure.adapters.input.rest.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.um.eventos.domain.model.Evento;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenEventosResponse {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private String categoria;
    private String espacioNombre;
    private String direccionEspacio;
    private List<PuntoInteresResponse> puntosInteres;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PuntoInteresResponse {
        private String nombre;
        private double distancia;
    }

    private List<Evento> eventos;
    
    public ResumenEventosResponse(List<Evento> eventos) {
        this.eventos = eventos;
    }

}