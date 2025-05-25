package com.um.espacios.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspacioDesactivadoEvent implements Serializable {
    private String espacioId;
    private Instant fechaDesactivacion;
}