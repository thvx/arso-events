package com.um.eventos.infrastructure.adapters.output.persistence.entities;

import com.um.eventos.domain.model.CategoriaEvento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "eventos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoEntity {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String organizador;

    @Column(nullable = false)
    private int plazas;

    @Column(nullable = false)
    private boolean cancelado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaEvento categoria;

    @Embedded
    private OcupacionEntity ocupacion;
}