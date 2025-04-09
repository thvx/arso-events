package domain.model;

import java.util.Objects;

public final class PuntoDeInteres {
    private final String nombre;
    private final String descripcion;
    private final double distancia; // en metros
    private final String urlWikipedia;

    public PuntoDeInteres(String nombre, String descripcion, double distancia, String urlWikipedia) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (distancia < 0) {
            throw new IllegalArgumentException("La distancia no puede ser negativa");
        }
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.distancia = distancia;
        this.urlWikipedia = urlWikipedia;
    }

    // Getters
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getDistancia() { return distancia; }
    public String getUrlWikipedia() { return urlWikipedia; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuntoDeInteres that = (PuntoDeInteres) o;
        return Double.compare(that.distancia, distancia) == 0 &&
                nombre.equals(that.nombre) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(urlWikipedia, that.urlWikipedia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, descripcion, distancia, urlWikipedia);
    }
}