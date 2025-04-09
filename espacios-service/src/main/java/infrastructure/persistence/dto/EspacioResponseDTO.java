package infrastructure.persistence.dto;

import java.util.List;

public class EspacioResponseDTO {
    public String id;
    public String nombre;
    public String propietario;
    public int capacidad;
    public String direccion;
    public double longitud;
    public double latitud;
    public String descripcion;
    public String estado;
    public List<PuntoDeInteresDTO> puntosDeInteres;
}
