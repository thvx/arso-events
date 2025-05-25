package com.um.espacios.infrastructure.adapters.output.persistence.entitiy;

import com.um.espacios.domain.model.EstadoEspacio;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import lombok.*;

@Document(collection = "espacios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EspacioFisicoDocument {
    @Id
    private String id;
    private String nombre;
    private String propietario;
    private int capacidad;
    private String descripcion;
    private EstadoEspacio estado;
    private double longitud;
    private double latitud;
    private String direccion;
    private List<PuntoDeInteresDocument> puntosDeInteres;
}