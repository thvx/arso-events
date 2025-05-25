package com.um.espacios.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuntosInteresAsignadosEvent implements Serializable {
    private String espacioId;
    private List<String> puntosInteresNombres;
    private Instant fechaAsignacion;

}