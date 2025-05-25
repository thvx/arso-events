package com.um.espacios.domain.events;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EspacioCreadoEvent implements Serializable {
    private String espacioId;
    private String nombre;
    private String propietario;
    private Instant fechaCreacion;

    public EspacioCreadoEvent(String id, String nombre, Instant now) {
    }
}