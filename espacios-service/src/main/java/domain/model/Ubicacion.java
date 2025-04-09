package domain.model;

import java.util.Objects;

public final class Ubicacion {
    private final double longitud;
    private final double latitud;
    private final String direccion;

    public Ubicacion(double longitud, double latitud, String direccion) {
        if (direccion == null || direccion.isBlank()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        this.longitud = longitud;
        this.latitud = latitud;
        this.direccion = direccion;
    }

    public double getLongitud() { return longitud; }
    public double getLatitud() { return latitud; }
    public String getDireccion() { return direccion; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return Double.compare(ubicacion.longitud, longitud) == 0 &&
                Double.compare(ubicacion.latitud, latitud) == 0 &&
                direccion.equals(ubicacion.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitud, latitud, direccion);
    }
}