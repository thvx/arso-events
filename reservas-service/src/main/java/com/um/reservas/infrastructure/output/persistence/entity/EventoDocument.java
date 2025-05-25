package com.um.reservas.infrastructure.output.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "eventos")
public class EventoDocument {
    @Id
    private String id;
    private int plazasDisponibles;
    private boolean cancelado;

    @DBRef
    private List<ReservaDocument> reservas = new ArrayList<>();
}