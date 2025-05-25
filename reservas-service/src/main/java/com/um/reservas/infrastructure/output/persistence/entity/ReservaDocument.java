package com.um.reservas.infrastructure.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservas")
public class ReservaDocument {
    @Id
    private String id;
    private String idUsuario;
    private int plazasReservadas;
    private boolean cancelada;

    @DBRef
    private EventoDocument evento;
}