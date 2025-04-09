package domain.model;

import domain.exceptions.EspacioConOcupacionesActivasException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

public class EspacioFisico {
    private String id;
    private String nombre;
    private final String propietario;
    private int capacidad;
    private final Ubicacion ubicacion;
    private List<PuntoDeInteres> puntosDeInteres;
    private String descripcion;
    private EstadoEspacio estado;

    // Constructor privado para usar el factory method
    public EspacioFisico(String nombre, String propietario, int capacidad,
                         Ubicacion ubicacion, String descripcion) {
        this.nombre = nombre;
        this.propietario = propietario;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.estado = EstadoEspacio.ACTIVO;
        this.puntosDeInteres = Collections.emptyList();
    }

    // Factory method
    public static EspacioFisico crearEspacio(String nombre, String propietario, int capacidad,
                                      double longitud, double latitud, String direccion,
                                      String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (propietario == null || propietario.isBlank()) {
            throw new IllegalArgumentException("El propietario no puede estar vacío");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }

        Ubicacion ubicacion = new Ubicacion(longitud, latitud, direccion);
        return new EspacioFisico(nombre, propietario, capacidad, ubicacion, descripcion);
    }

    // Métodos
    public void asignarPuntosDeInteres(List<PuntoDeInteres> puntos) {
        if (puntos == null) {
            throw new IllegalArgumentException("La lista de puntos no puede ser nula");
        }
        this.puntosDeInteres = List.copyOf(puntos);
    }

    public void modificarDatos(String nombre, int capacidad, String descripcion) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }

        this.nombre = nombre;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
    }

    public void desactivarEspacio(List<Ocupacion> ocupaciones) throws EspacioConOcupacionesActivasException {
        if (ocupaciones.stream().anyMatch(Ocupacion::estaActiva)) {
            throw new EspacioConOcupacionesActivasException(
                    "No se puede desactivar un espacio con ocupaciones activas");
        }
        this.estado = EstadoEspacio.CERRADO_TEMPORALMENTE;
    }

    public void activar() {
        this.estado = EstadoEspacio.ACTIVO;
    }

    public boolean estaDisponible(LocalDateTime inicio, LocalDateTime fin, List<Ocupacion> ocupaciones) {
        if (estado != EstadoEspacio.ACTIVO) {
            return false;
        }

        return ocupaciones.stream()
                .noneMatch(ocupacion ->
                        !ocupacion.isCancelada() &&
                                ocupacion.getFechaInicio().isBefore(fin) &&
                                ocupacion.getFechaFin().isAfter(inicio));
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPropietario() { return propietario; }
    public int getCapacidad() { return capacidad; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public List<PuntoDeInteres> getPuntosDeInteres() { return Collections.unmodifiableList(puntosDeInteres); }
    public String getDescripcion() { return descripcion; }
    public EstadoEspacio getEstado() { return estado; }

    // Setter solo para ID (usado por el repositorio)
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspacioFisico that = (EspacioFisico) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}