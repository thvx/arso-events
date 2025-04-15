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

    public void asignarPuntosDeInteres(List<PuntoDeInteres> puntos) {
        if (puntos == null) throw new IllegalArgumentException("La lista de puntos no puede ser nula");
        this.puntosDeInteres = List.copyOf(puntos);
    }

    public void modificarDatos(String nombre, int capacidad, String descripcion) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío");
        if (capacidad <= 0) throw new IllegalArgumentException("La capacidad debe ser mayor que cero");

        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public void desactivarEspacio(List<Ocupacion> ocupaciones) throws EspacioConOcupacionesActivasException {
        if (ocupaciones.stream().anyMatch(Ocupacion::estaActiva)) {
            throw new EspacioConOcupacionesActivasException("No se puede desactivar un espacio con ocupaciones activas");
        }
        this.estado = EstadoEspacio.CERRADO_TEMPORALMENTE;
    }

    public void activar() {
        this.estado = EstadoEspacio.ACTIVO;
    }

}
