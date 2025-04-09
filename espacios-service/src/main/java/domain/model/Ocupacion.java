package domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ocupacion {
    private String id;
    private final LocalDateTime fechaInicio;
    private final LocalDateTime fechaFin;
    private final EspacioFisico espacio;
    private boolean cancelada;

    public Ocupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacio) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }
        if (espacio == null) {
            throw new IllegalArgumentException("El espacio no puede ser nulo");
        }
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.espacio = espacio;
        this.cancelada = false;
    }

    public boolean estaActiva() {
        return !cancelada && fechaFin.isAfter(LocalDateTime.now());
    }

    public void cancelar() {
        this.cancelada = true;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public EspacioFisico getEspacio() { return espacio; }
    public boolean isCancelada() { return cancelada; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ocupacion ocupacion = (Ocupacion) o;
        return Objects.equals(id, ocupacion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}