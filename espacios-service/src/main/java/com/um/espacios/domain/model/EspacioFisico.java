package com.um.espacios.domain.model;

import com.um.espacios.domain.exceptions.EspacioConOcupacionesActivasException;
import lombok.*;

import java.util.List;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class EspacioFisico {
    @EqualsAndHashCode.Include
    private String id;
    private String nombre;
    private final String propietario;
    private int capacidad;
    private final Ubicacion ubicacion;
    private List<PuntoDeInteres> puntosDeInteres;
    private String descripcion;
    private EstadoEspacio estado;

    // Constructor completo
    public EspacioFisico(String id, String nombre, String propietario, int capacidad,
                         Ubicacion ubicacion, List<PuntoDeInteres> puntosDeInteres,
                         String descripcion, EstadoEspacio estado) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.puntosDeInteres = puntosDeInteres;
        this.descripcion = descripcion;
        this.estado = estado != null ? estado : EstadoEspacio.ACTIVO;
    }

    // Método Factory para creación
    public static EspacioFisico crearEspacio(String nombre, String propietario, int capacidad,
                                             double longitud, double latitud, String direccion,
                                             String descripcion) {
        Ubicacion ubicacion = Ubicacion.builder()
                .longitud(longitud)
                .latitud(latitud)
                .direccion(direccion)
                .build();

        return new EspacioFisico(
                null, // id se generará después
                nombre,
                propietario,
                capacidad,
                ubicacion,
                null, // puntosDeInteres inicialmente null
                descripcion,
                EstadoEspacio.ACTIVO
        );
    }

    public void activar() throws Exception {
        if (estado == EstadoEspacio.CERRADO_TEMPORALMENTE) {
            estado = EstadoEspacio.ACTIVO;
        } else {
            throw new Exception("El espacio ya está activo");
        }
    }

    public void desactivar() throws Exception {
        if (estado == EstadoEspacio.ACTIVO) {
            estado = EstadoEspacio.CERRADO_TEMPORALMENTE;
        } else {
            throw new Exception("El espacio ya está inactivo");
        }
    }

    public void modificarDatos(String nombre, int capacidad, String descripcion) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public void asignarPuntosDeInteres(List<PuntoDeInteres> collect) {
        if (this.puntosDeInteres == null) {
            this.puntosDeInteres = collect;
        } else {
            this.puntosDeInteres.addAll(collect);
        }
    }
}
