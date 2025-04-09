package infrastructure.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "puntos_de_interes")
public class PuntoDeInteresEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nombre;
    private String descripcion;
    private double distancia;
    private String urlWikipedia;

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getUrlWikipedia() {
        return urlWikipedia;
    }

    public void setUrlWikipedia(String urlWikipedia) {
        this.urlWikipedia = urlWikipedia;
    }
}
